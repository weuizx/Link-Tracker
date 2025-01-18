package edu.java.scrapper.service;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.client.dto.GitHubCommitResponse;
import edu.java.scrapper.client.dto.GitHubRepositoryResponse;
import edu.java.scrapper.client.dto.StackOverflowAnswerResponse;
import edu.java.scrapper.client.dto.StackOverflowQuestionResponse;
import edu.java.scrapper.controllers.dto.UpdateLinksRequest;
import edu.java.scrapper.repositories.dto.Link;
import edu.java.scrapper.repositories.jdbc.JdbcChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class LinkUpdaterScheduler {

    private final LinkUpdater linkUpdater;

    private final GitHubClient gitHubClient;

    private final StackOverflowClient stackOverflowClient;

    private final BotClient botClient;


    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void update() {
        log.info("LinkUpdaterScheduler обновляет ссылки");
        List<Link> links = linkUpdater.listAllOldCheckedLinks();
        for (Link link : links) {
            checkLink(link);
            linkUpdater.update(link.id());
        }
    }

    private void checkLink(Link link) {
        URI url = URI.create(link.url());
        String host = url.getHost();

        if (host.equals("github.com")) {
            gitHubHandle(link, url);
        } else if (host.equals("stackoverflow.com")) {
            stackOverflowHandle(link, url);
        }
    }

    private void gitHubHandle(Link link, URI url) {
        String[] path = url.getPath().split("/");
        String owner = path[1];
        String repo = path[2];
        GitHubRepositoryResponse repositoryResponse = gitHubClient.fetchRepository(owner, repo);
        if(link.lastCheckTime().isBefore(repositoryResponse.pushedAt()) ||
            link.lastCheckTime().isBefore(repositoryResponse.updatedAt())) {
            String description = "Обновление в репозитории";
            GitHubCommitResponse commits = gitHubClient.fetchCommit(owner, repo);
            if(commits.items().size() != link.commitsCount()){
                description += (": новый коммит:" + commits.items().getFirst().commit().message());
            }
            List<Long> tgChatIds = linkUpdater.listAllTgChatIdByLinkId(link.id());
            sendBotUpdates(link, description, tgChatIds);
        }
}

private void stackOverflowHandle(Link link, URI url) {
    Long questionId = Long.parseLong(url.getPath().split("/")[2]);
    StackOverflowQuestionResponse.StackOverflowQuestionItem res =
        stackOverflowClient.fetchQuestion(questionId).items().getFirst();
    if (link.lastCheckTime().isBefore(res.lastActivityDate())) {

        String description = "Новая информация по вопросу";

        StackOverflowAnswerResponse answers = stackOverflowClient.fetchAnswer(questionId);
        if (answers.items().size() != link.answersCount()) {
            description += (": появился новый ответ в " + answers.items().getFirst().creationDate());
        }

        List<Long> tgChatIds = linkUpdater.listAllTgChatIdByLinkId(link.id());
        sendBotUpdates(link, description, tgChatIds);
    }
}

private void sendBotUpdates(Link link, String description, List<Long> tgChatIds) {
    botClient.fetchUpdateLinks(new UpdateLinksRequest(
        link.id(),
        link.url(),
        description,
        tgChatIds
    ));
}
}

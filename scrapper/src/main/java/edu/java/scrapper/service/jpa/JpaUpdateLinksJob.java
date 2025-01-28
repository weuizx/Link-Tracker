package edu.java.scrapper.service.jpa;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.client.dto.GitHubRepositoryResponse;
import edu.java.scrapper.client.dto.StackOverflowQuestionResponse;
import edu.java.scrapper.client.dto.NotifyUsersDtoOut;
import edu.java.scrapper.repositories.jpa.LinkRepository;
import edu.java.scrapper.repositories.jpa.entity.Client;
import edu.java.scrapper.repositories.jpa.entity.Link;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class JpaUpdateLinksJob {

    private final LinkRepository linkRepository;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final BotClient botClient;

    private static final Long MINUTES = 10L;
    private static final String GITHUB_UPDATE_MESSAGE = "Обновление в репозитории";
    private static final String STACKOVERFLOW_UPDATE_MESSAGE = "Новая информация по вопросу";

    private static final Map<String, Consumer<Link>> handlerMap = new HashMap<>();

    {
        handlerMap.put("github.com", this::gitHubHandle);
        handlerMap.put("stackoverflow.com", this::stackOverflowHandle);
    }

    @Scheduled(fixedDelayString = "#{@scheduler.interval}")
    public void checkUpdates() {

        log.info("Process of checking for resource updates started");
        List<Link> links = linkRepository.findAllByLastCheckDatetimeBefore(ZonedDateTime.now().minusMinutes(MINUTES));
        log.info("{} links found", links.size());
        for (Link link : links) {

            log.debug("Processing link : {}", link.toString());

            //TODO validate
            URI url = URI.create(link.getUrl());
            String host = url.getHost();
            handlerMap.get(host).accept(link);

            link.setLastCheckDatetime(ZonedDateTime.now());
            linkRepository.save(link);
        }

        log.info("Process of checking for resource updates ended");
    }

    private void sendBotUpdates(Link link, String description, List<Long> tgChatIds) {
        botClient.fetchUpdateLinks(new NotifyUsersDtoOut(
            link.getUrl(),
            description,
            tgChatIds
        ));
    }

    private void gitHubHandle(Link link) {

        log.info("GitHub handler started");
        String[] path = URI.create(link.getUrl()).getPath().split("/");
        String owner = path[1];
        String repo = path[2];

        GitHubRepositoryResponse repositoryResponse = gitHubClient.fetchRepository(owner, repo);

        if (link.getLastCheckDatetime().isBefore(repositoryResponse.pushedAt()) ||
            link.getLastCheckDatetime().isBefore(repositoryResponse.updatedAt())) {

            List<Long> tgChatIds = link.getClients().stream().map(Client::getTgChatId).toList();

            sendBotUpdates(link, GITHUB_UPDATE_MESSAGE, tgChatIds);

        }
    }

    private void stackOverflowHandle(Link link) {

        log.info("StackOverflow handler started");
        String[] path = URI.create(link.getUrl()).getPath().split("/");
        Long questionId = Long.parseLong(path[2]);

        StackOverflowQuestionResponse.StackOverflowQuestionItem res =
            stackOverflowClient.fetchQuestion(questionId).items().getFirst();

        if (link.getLastCheckDatetime().isBefore(res.lastActivityDate())) {

            List<Long> tgChatIds = link.getClients().stream().map(Client::getTgChatId).toList();
            sendBotUpdates(link, STACKOVERFLOW_UPDATE_MESSAGE, tgChatIds);
        }
    }

}

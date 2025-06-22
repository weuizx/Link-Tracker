package edu.java.scrapper.service.jpa;

import edu.java.scrapper.client.BotClient;
import edu.java.scrapper.client.GitHubClient;
import edu.java.scrapper.client.HabrClient;
import edu.java.scrapper.client.StackOverflowClient;
import edu.java.scrapper.client.YouTubeClient;
import edu.java.scrapper.client.dto.GitHubRepositoryResponse;
import edu.java.scrapper.client.dto.HabrRssResponse;
import edu.java.scrapper.client.dto.NotifyUsersDtoOut;
import edu.java.scrapper.client.dto.StackOverflowQuestionResponse;
import edu.java.scrapper.client.dto.YouTubeChannelResponse;
import edu.java.scrapper.client.dto.YouTubePlaylistResponse;
import edu.java.scrapper.repositories.jpa.LinkRepository;
import edu.java.scrapper.repositories.jpa.entity.Client;
import edu.java.scrapper.repositories.jpa.entity.Link;
import java.net.URI;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JpaUpdateLinksJob {

    private final LinkRepository linkRepository;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final YouTubeClient youTubeClient;
    private final HabrClient habrClient;
    private final BotClient botClient;
    @Value("${app.scheduler.force-check-delay:600000}")
    private Long millis;

    private static final String PLAYLIST_PATTERN = "playlist";
    private static final String GITHUB_UPDATE_MESSAGE = "Обновление в репозитории";
    private static final String STACKOVERFLOW_UPDATE_MESSAGE = "Новая информация по вопросу";
    private static final String YOUTUBE_CHANNEL_UPDATE_MESSAGE = "Новое видео на канале";
    private static final String YOUTUBE_PLAYLIST_UPDATE_MESSAGE = "Новое видео добавлено в плейлист";
    private static final String HABR_UPDATE_MESSAGE = "Автор опубликовал новую статью";

    private static final DateTimeFormatter
        rssDateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);

    private final Map<String, Consumer<Link>> handlerMap = Map.of(
        "github.com", this::gitHubHandle,
        "stackoverflow.com", this::stackOverflowHandle,
        "www.youtube.com", this::youTubeHandle,
        "habr.com", this::habrHandle
    );

    @Scheduled(fixedDelayString = "${app.scheduler.interval}")
    public void checkUpdates() {

        log.info("Process of checking for resource updates started");
        List<Link> links =
            linkRepository.findAllByLastCheckDatetimeBefore(ZonedDateTime.now().minusSeconds(millis / 1000));
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

    private void youTubeHandle(Link link) {

        log.info("YouTube handler started");
        String playlistOrChannelId = URI.create(link.getUrl()).getPath().split("/")[1];
        if (playlistOrChannelId.equalsIgnoreCase(PLAYLIST_PATTERN)) {
            YouTubePlaylistResponse response = youTubeClient.fetchPlaylist(extractPlaylistId(link.getUrl()));
            if (response.items().isEmpty()) {
                return;
            }
            Long videosCount = response.items().getFirst().contentDetails().itemCount();
            if (link.getVideosCount() != null && link.getVideosCount() < videosCount) {
                List<Long> tgChatIds = link.getClients().stream().map(Client::getTgChatId).toList();
                sendBotUpdates(link, YOUTUBE_PLAYLIST_UPDATE_MESSAGE, tgChatIds);
            }
            link.setVideosCount(videosCount);
        } else {
            YouTubeChannelResponse response = youTubeClient.fetchChannel(playlistOrChannelId);
            if (response.items().isEmpty()) {
                return;
            }
            Long videosCount = response.items().getFirst().statistics().videoCount();
            if (link.getVideosCount() != null && link.getVideosCount() < videosCount) {
                List<Long> tgChatIds = link.getClients().stream().map(Client::getTgChatId).toList();
                sendBotUpdates(link, YOUTUBE_CHANNEL_UPDATE_MESSAGE, tgChatIds);
            }
            link.setVideosCount(videosCount);
        }
        linkRepository.save(link);
    }

    private void habrHandle(Link link) {

        log.info("Habr handler started");
        String username = URI.create(link.getUrl()).getPath().split("/")[3];
        HabrRssResponse response = habrClient.fetchUserArticles(username);
        ZonedDateTime lastPublishedDateTime = response.channel().items().stream()
            .map(HabrRssResponse.Item::pubDate)
            .map(dateStr -> ZonedDateTime.parse(dateStr, rssDateFormatter))
            .max(ZonedDateTime::compareTo)
            .orElse(null);
        if (lastPublishedDateTime != null && link.getLastCheckDatetime().isBefore(lastPublishedDateTime)) {
            List<Long> tgChatIds = link.getClients().stream().map(Client::getTgChatId).toList();
            sendBotUpdates(link, HABR_UPDATE_MESSAGE, tgChatIds);
        }
    }

    private String extractPlaylistId(String url) {
        try {
            URI uri = new URI(url);
            String query = uri.getQuery();
            if (query == null || query.isEmpty()) {
                return "";
            }

            return Arrays.stream(query.split("&"))
                .filter(param -> param.startsWith("list="))
                .findFirst()
                .map(param -> param.substring(5)).get();
        } catch (Exception e) {
            log.info("Unexpected error", e);
            return "";
        }
    }

}

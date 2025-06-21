package edu.java.scrapper.client;

import edu.java.scrapper.client.dto.YouTubeChannelResponse;
import edu.java.scrapper.client.dto.YouTubePlaylistResponse;
import edu.java.scrapper.configuration.ApplicationConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class YouTubeClientImpl implements YouTubeClient {

    private final WebClient webClient;

    public YouTubeClientImpl(ApplicationConfig applicationConfig) {
        this.webClient = WebClient.builder()
            .baseUrl(applicationConfig.urls().youTubeBaseUrl())
            .defaultHeader("Accept", "application/json")
            .defaultUriVariables(java.util.Map.of("key", applicationConfig.credentials().youTubeKey()))
            .build();
    }

    @Override
    public YouTubePlaylistResponse fetchPlaylist(String playlistId) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/playlists")
                .queryParam("part", "snippet,contentDetails")
                .queryParam("id", playlistId)
                .queryParam("key", "{key}")
                .build())
            .retrieve()
            .bodyToMono(YouTubePlaylistResponse.class)
            .block();
    }

    @Override
    public YouTubeChannelResponse fetchChannel(String channelId) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/channels")
                .queryParam("part", "snippet,statistics")
                .queryParam("forHandle", channelId)
                .queryParam("key", "{key}")
                .build())
            .retrieve()
            .bodyToMono(YouTubeChannelResponse.class)
            .block();
    }
}

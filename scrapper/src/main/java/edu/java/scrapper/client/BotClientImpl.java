package edu.java.scrapper.client;

import edu.java.scrapper.configuration.ApplicationConfig;
import edu.java.scrapper.client.dto.NotifyUsersDtoOut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BotClientImpl implements BotClient{
    private final WebClient webClient;

    public BotClientImpl(ApplicationConfig applicationConfig) {
        this.webClient = WebClient.builder()
            .baseUrl(applicationConfig.urls().botBaseUrl())
            .build();
    }
    @Override
    public ResponseEntity<String> fetchUpdateLinks(NotifyUsersDtoOut requestBody) {
        String response = webClient.post()
            .uri("/updates")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        return ResponseEntity.ok(response);
    }
}

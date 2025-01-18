package edu.java.bot.client;

import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.controllers.dto.AddLinkRequest;
import edu.java.bot.controllers.dto.LinkResponse;
import edu.java.bot.controllers.dto.ListLinksResponse;
import edu.java.bot.controllers.dto.RemoveLinkRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ScrapperClientImpl implements ScrapperClient{
    private final WebClient webClient;

    public ScrapperClientImpl(ApplicationConfig applicationConfig) {
        this.webClient = WebClient.builder()
            .baseUrl(applicationConfig.scrapperBaseUrl())
            .build();
    }
    @Override
    public ResponseEntity<String> fetchRegisterChat(int id) {
        String response = webClient.post()
            .uri("/tg-chat/{id}", id)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> fetchDeleteChat(int id) {
        String response = webClient.delete()
            .uri("tg-chat/{id}", id)
            .retrieve()
            .bodyToMono(String.class)
            .block();
        return ResponseEntity.ok(response);
    }

    @Override
    public ListLinksResponse getLinks(int tgChatId) {
        ListLinksResponse response = webClient.get()
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(tgChatId))
            .retrieve()
            .bodyToMono(ListLinksResponse.class)
            .block();
        return response;
    }

    @Override
    public ResponseEntity<LinkResponse> addLink(int tgChatId, AddLinkRequest requestBody) {
        LinkResponse response = webClient.post()
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(tgChatId))
            .body(Mono.just(requestBody), AddLinkRequest.class)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<LinkResponse> removeLink(int tgChatId, RemoveLinkRequest requestBody) {
        LinkResponse response = webClient.method(HttpMethod.DELETE)
            .uri("/links")
            .header("Tg-Chat-Id", String.valueOf(tgChatId))
            .body(Mono.just(requestBody), AddLinkRequest.class)
            .retrieve()
            .bodyToMono(LinkResponse.class)
            .block();
        return ResponseEntity.ok(response);
    }
}

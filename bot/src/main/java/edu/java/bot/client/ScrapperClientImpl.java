package edu.java.bot.client;

import edu.java.bot.client.dto.ClientDtoIn;
import edu.java.bot.client.dto.LinkDto;
import edu.java.bot.configuration.ApplicationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ScrapperClientImpl implements ScrapperClient {

    private final RestTemplate restTemplate;

    private final String BASE_URL;

    @Autowired
    public ScrapperClientImpl(ApplicationConfig applicationConfig) {
        this.restTemplate = new RestTemplate();
        BASE_URL = applicationConfig.scrapperBaseUrl() + "/tg-chat";
    }

    @Override
    public ClientDtoIn registerChat(long tgChatId) {

        String url = BASE_URL + "/{id}";
        try {
            return restTemplate.postForObject(url, null, ClientDtoIn.class, tgChatId);
        } catch (RestClientException e) {
            log.info("Error while requesting {}. {}", url, e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteChat(long tgChatId) {

        String url = BASE_URL + "/{id}";
        try {
            restTemplate.delete(url, tgChatId);
        } catch (RestClientException e) {
            log.info("Error while requesting {}. {}", url, e.getMessage());
            throw e;
        }
    }

    @Override
    public ClientDtoIn getUserInfo(long tgChatId) {

        String url = BASE_URL + "/{id}";
        try {
            return restTemplate.getForObject(url, ClientDtoIn.class, tgChatId);
        } catch (RestClientException e) {
            log.info("Error while requesting {}. {}", url, e.getMessage());
            return null;
        }

    }

    @Override
    public ClientDtoIn addLink(long tgChatId, LinkDto linkDto) {

        String url = BASE_URL + "/{id}" + "/add-link";
        HttpEntity<LinkDto> httpEntity = new HttpEntity<>(linkDto);
        try {
            ResponseEntity<ClientDtoIn> response =
                restTemplate.exchange(url, HttpMethod.PUT, httpEntity, ClientDtoIn.class, tgChatId);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.info("Error while requesting {}. StatusCode {}", url, response.getStatusCode());
                return null;
            }
        } catch (RestClientException e) {
            log.info("Error while requesting {}. {}", url, e.getMessage());
            return null;
        }
    }

    @Override
    public ClientDtoIn removeLink(long tgChatId, LinkDto linkDto) {

        String url = BASE_URL + "/{id}" + "/remove-link";
        HttpEntity<LinkDto> httpEntity = new HttpEntity<>(linkDto);
        try {
            ResponseEntity<ClientDtoIn> response =
                restTemplate.exchange(url, HttpMethod.PUT, httpEntity, ClientDtoIn.class, tgChatId);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.info("Error while requesting {}. StatusCode {}", url, response.getStatusCode());
                return null;
            }
        } catch (RestClientException e) {
            log.info("Error while requesting {}. {}", url, e.getMessage());
            return null;
        }
    }
}

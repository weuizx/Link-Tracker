package edu.java.scrapper.client;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import edu.java.scrapper.client.dto.HabrRssResponse;
import edu.java.scrapper.configuration.ApplicationConfig;
import java.util.Collections;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HabrClientImpl implements HabrClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final XmlMapper xmlMapper;

    public HabrClientImpl(ApplicationConfig applicationConfig) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = applicationConfig.urls().habrBaseUrl();
        this.xmlMapper = new XmlMapper();
    }

    @Override
    public HabrRssResponse fetchUserArticles(String username) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
        headers.set("User-Agent", "Mozilla/5.0");

        String url = baseUrl + "/ru/rss/users/" + username + "/publications/articles/";

        try {
            String xmlResponse = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
            ).getBody();

            return xmlMapper.readValue(xmlResponse, HabrRssResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch or parse Habr RSS feed", e);
        }
    }
}

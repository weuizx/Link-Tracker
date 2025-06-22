package edu.java.scrapper.client;

import edu.java.scrapper.client.dto.HabrRssResponse;

public interface HabrClient {

    HabrRssResponse fetchUserArticles(String username);

}

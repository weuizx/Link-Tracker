package edu.java.scrapper.client;

import edu.java.scrapper.controllers.dto.UpdateLinksRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface BotClient {
    ResponseEntity<String> fetchUpdateLinks(@RequestBody UpdateLinksRequest requestBody);
}

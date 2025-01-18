package edu.java.bot.client;

import edu.java.bot.controllers.dto.AddLinkRequest;
import edu.java.bot.controllers.dto.LinkResponse;
import edu.java.bot.controllers.dto.ListLinksResponse;
import edu.java.bot.controllers.dto.RemoveLinkRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface ScrapperClient {
    ResponseEntity<String> fetchRegisterChat(@PathVariable("id") int id);
    ResponseEntity<String> fetchDeleteChat(@PathVariable("id") int id);
    ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") int tgChatId);
    ResponseEntity<LinkResponse> addLink(
        @RequestHeader("Tg-Chat-Id") int tgChatId,
        @RequestBody AddLinkRequest requestBody);
    ResponseEntity<LinkResponse> removeLink(
        @RequestHeader("Tg-Chat-Id") int tgChatId,
        @RequestBody RemoveLinkRequest requestBody);
}

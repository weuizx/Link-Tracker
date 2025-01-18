package edu.java.scrapper.controllers;

import edu.java.scrapper.controllers.dto.AddLinkRequest;
import edu.java.scrapper.controllers.dto.LinkResponse;
import edu.java.scrapper.controllers.dto.ListLinksResponse;
import edu.java.scrapper.controllers.dto.RemoveLinkRequest;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.InvalidRequestParametersException;
import edu.java.scrapper.exceptions.LinkExistsException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import edu.java.scrapper.repositories.dto.Link;
import edu.java.scrapper.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LinkController {
    private final LinkService linkService;

    @GetMapping("/links")
    public ListLinksResponse getLinks(@RequestHeader("Tg-Chat-Id") long tgChatId) throws ChatNotFoundException {
        List<LinkResponse> response = linkService.listAll(tgChatId).stream()
            .map((link) -> new LinkResponse(link.id(), URI.create(link.url())))
            .toList();
        return new ListLinksResponse(response);
    }

    @PostMapping("/links")
    public ResponseEntity<LinkResponse> addLink(@RequestHeader("Tg-Chat-Id") long tgChatId, @RequestBody AddLinkRequest requestBody)
        throws InvalidRequestParametersException, ChatNotFoundException, LinkExistsException {
        URI url;
        Link link;
        try {
            url = new URI(requestBody.link());
            link = linkService.add(tgChatId, url);
        } catch (URISyntaxException e) {
            throw new InvalidRequestParametersException("Некорректные параметры запроса");
        }
        return ResponseEntity.ok().body(new LinkResponse(link.id(), URI.create(link.url())));
    }

    @DeleteMapping("/links")
    public ResponseEntity<LinkResponse> removeLink(@RequestHeader("Tg-Chat-Id") long tgChatId, @RequestBody RemoveLinkRequest requestBody)
        throws InvalidRequestParametersException, ChatNotFoundException, LinkNotFoundException {
            URI url;
            Link link;
            try {
                url = new URI(requestBody.link());
                link = linkService.remove(tgChatId, url);
            } catch (URISyntaxException e) {
                throw new InvalidRequestParametersException("Некорректные параметры запроса");
            }
            return ResponseEntity.ok().body(new LinkResponse(link.id(), URI.create(link.url())));
    }
}

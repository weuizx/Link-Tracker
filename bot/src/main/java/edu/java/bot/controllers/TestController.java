package edu.java.bot.controllers;

import edu.java.bot.client.ScrapperClient;
import edu.java.bot.client.dto.ClientDtoIn;
import edu.java.bot.client.dto.LinkDto;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final ScrapperClient scrapperClient;

    @GetMapping("/register-chat")
    ResponseEntity<ClientDtoIn> registerChat(@Parameter int tgChatId){
        return ResponseEntity.ok(scrapperClient.registerChat(tgChatId));
    }

    @GetMapping("/delete-chat")
    void deleteChat(@Parameter int tgChatId){
        scrapperClient.deleteChat(tgChatId);
    }

    @GetMapping("/get-links")
    ResponseEntity<ClientDtoIn> getLinks(@Parameter int tgChatId){
        return ResponseEntity.ok(scrapperClient.getUserInfo(tgChatId));
    }

    @PutMapping("/add-link")
    ResponseEntity<ClientDtoIn> addLink(@Parameter int tgChatId, @RequestBody LinkDto linkDto){
        return ResponseEntity.ok(scrapperClient.addLink(tgChatId, linkDto));
    }

    @PutMapping("/remove-link")
    ResponseEntity<ClientDtoIn> removeLink(@Parameter int tgChatId, @RequestBody LinkDto linkDto){
        return ResponseEntity.ok(scrapperClient.removeLink(tgChatId, linkDto));
    }
}

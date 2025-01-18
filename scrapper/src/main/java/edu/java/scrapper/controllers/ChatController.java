package edu.java.scrapper.controllers;

import edu.java.scrapper.exceptions.ChatExistsException;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.InvalidRequestParametersException;
import edu.java.scrapper.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/tg-chat/{id}")
    public ResponseEntity<String> registerChat(@PathVariable("id") long id) throws ChatExistsException {
        chatService.register(id);
        return ResponseEntity.ok("Чат успешно зарегистрирован");
    }

    @DeleteMapping("/tg-chat/{id}")
    public ResponseEntity<String> deleteChat(@PathVariable("id") long id) throws ChatNotFoundException {
        chatService.unregister(id);
        return ResponseEntity.ok("Чат успешно удален");
    }
}

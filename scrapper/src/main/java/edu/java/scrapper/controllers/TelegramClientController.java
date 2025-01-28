package edu.java.scrapper.controllers;

import edu.java.scrapper.controllers.dto.ClientDtoOut;
import edu.java.scrapper.controllers.dto.ClientMapper;
import edu.java.scrapper.controllers.dto.LinkDto;
import edu.java.scrapper.exceptions.ChatExistsException;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.InvalidRequestParametersException;
import edu.java.scrapper.exceptions.LinkExistsException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import edu.java.scrapper.service.jpa.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tg-chat")
public class TelegramClientController {

    private final UserServiceImpl userService;
    private final ClientMapper clientMapper;

    @PostMapping("/{id}")
    public ResponseEntity<ClientDtoOut> registerChat(@PathVariable("id") long tgChatId) throws ChatExistsException {
        return ResponseEntity.ok(clientMapper.clientToClientDtoOut(userService.register(tgChatId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChat(@PathVariable("id") long tgChatId) throws ChatNotFoundException {
        userService.unregister(tgChatId);
        return ResponseEntity.ok("Чат успешно удален");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDtoOut> getUserInfo(@PathVariable("id") long tgChatId) throws ChatNotFoundException {
        return ResponseEntity.ok(clientMapper.clientToClientDtoOut(userService.getUserInfo(tgChatId)));
    }

    @PutMapping("/{id}/add-link")
    public ResponseEntity<ClientDtoOut> addLink(@PathVariable("id") long tgChatId, @RequestBody LinkDto linkDto)
        throws InvalidRequestParametersException, ChatNotFoundException, LinkExistsException {

        return ResponseEntity.ok(clientMapper.clientToClientDtoOut(userService.addLinkForUser(tgChatId, linkDto.getUrl())));
    }

    @PutMapping("/{id}/remove-link")
    public ResponseEntity<ClientDtoOut> removeLink(@PathVariable("id") long tgChatId, @RequestBody LinkDto linkDto)
        throws InvalidRequestParametersException, ChatNotFoundException, LinkNotFoundException {

        return ResponseEntity.ok(clientMapper.clientToClientDtoOut(userService.removeLinkForUser(tgChatId, linkDto.getUrl())));
    }
}

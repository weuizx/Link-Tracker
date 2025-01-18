package edu.java.bot.controllers;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.controllers.dto.UpdateLinksRequest;
import edu.java.bot.controllers.exceptions.InvalidRequestParametersException;
import edu.java.bot.telegram.MyTelegramBot;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class LinkUpdateController {
    @Autowired
    private MyTelegramBot telegramBot;

    @PostMapping("/updates")
    public ResponseEntity<String> updateLinks(@Valid @RequestBody UpdateLinksRequest requestBody){
        if(!ObjectUtils.isEmpty(requestBody.tgChatIds())){
            for(long id : requestBody.tgChatIds()) {
                telegramBot.execute(new SendMessage(id, requestBody.description() + requestBody.url()));
            }
            return ResponseEntity.ok("Обновление обработано");
        }else{
            throw new InvalidRequestParametersException("Некорректные параметры запроса");
        }
    }
}

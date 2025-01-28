package edu.java.bot.service;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.controllers.dto.NotifyUsersDtoIn;
import edu.java.bot.controllers.exceptions.InvalidRequestParametersException;
import edu.java.bot.telegram.MyTelegramBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl {

    private final MyTelegramBot telegramBot;

    public void notifyUsers(NotifyUsersDtoIn request) {

        List<Long> tgChatIds = request.tgChatIds();
        log.info("tgChatIds : {}", tgChatIds);
        if (ObjectUtils.isEmpty(tgChatIds)) {
            throw new InvalidRequestParametersException("Некорректные параметры запроса");
        }

        for (long id : tgChatIds) {
            telegramBot.execute(new SendMessage(id, request.description() + " " + request.url()));
        }

    }
}

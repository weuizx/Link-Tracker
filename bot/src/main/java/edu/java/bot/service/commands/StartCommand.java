package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StartCommand implements Command {
    private static final Logger log = LoggerFactory.getLogger(StartCommand.class);

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String description() {
        return "Начать работу с ботом";
    }

    @Override
    public SendMessage handle(Update update) {

        String messageText = "Привет";
        log.info("chatId : " + update.message().chat().id());
        return new SendMessage(update.message().chat().id(), messageText);
    }
}


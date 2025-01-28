package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.client.dto.ClientDtoIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartCommand implements Command {

    private final ScrapperClient scrapperClient;

    private static final String COMMAND = "/start";
    private static final String DESCRIPTION = "Начать работу с ботом";
    private static final String ALREADY_REGISTERED = "Ваш аккаунт уже зарегистрирован!\n" +
        "Введите /help для просмотра списка доступных команд.";
    private static final String WELCOME_MESSAGE = "Привет! Ваш аккаунт успешно зарегистрирован.\nЭтот бот предназначен для отслеживания обновлений на " +
        "интернет-ресурсах. Введите /help для просмотра списка доступных команд.";
    private static final String ERROR_MESSAGE = "Что-то пошло не так. Попробуйте позже.";


    @Override
    public String command() {
        return COMMAND;
    }

    @Override
    public String description() {
        return DESCRIPTION;
    }

    @Override
    public SendMessage handle(Update update) {

        long tgChatId = update.message().chat().id();
        log.debug("StartCommand : username(tgChatId) :{}({})", update.message().chat().username(), tgChatId);

        ClientDtoIn response = scrapperClient.getUserInfo(tgChatId);

        if (!Objects.isNull(response)){
            return new SendMessage(update.message().chat().id(), ALREADY_REGISTERED);
        }

        response = scrapperClient.registerChat(tgChatId);

        return new SendMessage(update.message().chat().id(), Objects.isNull(response) ? ERROR_MESSAGE : WELCOME_MESSAGE);
    }
}


package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.client.dto.ClientDtoIn;
import edu.java.bot.client.dto.LinkDto;
import edu.java.bot.util.UrlChecker;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrackCommand implements Command {

    private final ScrapperClient scrapperClient;

    private static final String COMMAND = "/track";
    ;
    private static final String DESCRIPTION = "Начать отслеживание ресурса";
    private static final String SUCCESS_MESSAGE = "Ссылка успешно добавлена к отслеживанию";
    private static final String CLARIFYING_MESSAGE =
        "Для добавления ссылки к отслеживанию после команды /track через пробел введите " +
            "ссылку для отслеживания\n/track %ссылка для отслеживания%";
    private static final String ALREADY_TRACKING_MESSAGE = "Ссылка уже отслеживается. Введите /list для просмотра " +
        "отслеживаемых ресурсов";
    private static final String NOT_VALID_MESSAGE = "Вы ввели некорректную ссылку. Ссылка должна начинаться с " +
        "(http|https)://";

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
        log.debug("TrackCommand : username(tgChatId) :{}({})", update.message().chat().username(), tgChatId);

        ClientDtoIn response = scrapperClient.getUserInfo(tgChatId);
        if (Objects.isNull(response)) {
            return new SendMessage(tgChatId, USER_NOT_FOUND_MESSAGE);
        }

        String input = update.message().text();
        int spaceIndex = input.indexOf(' ');

        if (spaceIndex == -1) {
            return new SendMessage(update.message().chat().id(), CLARIFYING_MESSAGE);
        }

        String url = input.substring(spaceIndex + 1);
        if (!UrlChecker.isValid(url)) {
            return new SendMessage(tgChatId, NOT_VALID_MESSAGE);
        }

        response = scrapperClient.addLink(tgChatId, new LinkDto(url));
        if (Objects.isNull(response)) {
            return new SendMessage(tgChatId, ALREADY_TRACKING_MESSAGE);
        }

        return new SendMessage(tgChatId, SUCCESS_MESSAGE + " " + url);
    }
}

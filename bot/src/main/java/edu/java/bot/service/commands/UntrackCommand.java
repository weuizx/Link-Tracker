package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.client.dto.ClientDtoIn;
import edu.java.bot.client.dto.LinkDto;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UntrackCommand implements Command {

    private final ScrapperClient scrapperClient;

    private static final String COMMAND = "/untrack";
    private static final String DESCRIPTION = "Прекратить отслеживание ресурса";
    private static final String SUCCESS_MESSAGE = "Ссылка успешно удалена из отслеживания";
    private static final String CLARIFYING_MESSAGE =
        "Для прекращения отслеживания ссылки после команды /untrack введите " +
            "ссылку через пробел\n/untrack %ссылка%";
    private static final String LINK_NOT_FOUND_MESSAGE =
        "Данная ссылка не отслеживалась. Введите /list для просмотра " +
            "отслеживаемых ресурсов";

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
        log.debug("UntrackCommand : username(tgChatId) :{}({})", update.message().chat().username(), tgChatId);

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
        response = scrapperClient.removeLink(tgChatId, new LinkDto(url));
        if (Objects.isNull(response)) {
            return new SendMessage(tgChatId, LINK_NOT_FOUND_MESSAGE);
        }

        return new SendMessage(tgChatId, SUCCESS_MESSAGE + " " + url);
    }
}

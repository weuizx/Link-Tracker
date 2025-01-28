package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.client.dto.ClientDtoIn;
import edu.java.bot.client.dto.LinkDtoIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ListCommand implements Command {

    private final ScrapperClient scrapperClient;

    private static final String COMMAND = "/list";
    private static final String DESCRIPTION = "Показать список отслеживаемых ресурсов";
    private static final String EMPTY_SET_MESSAGE = "Список отслеживаемых ресурсов пуст!\nИспользуйте /track чтобы добавить ссылку " +
        "к отслеживанию";

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
        log.debug("ListCommand : username(tgChatId) :{}({})", update.message().chat().username(), tgChatId);

        ClientDtoIn response = scrapperClient.getUserInfo(tgChatId);
        if (Objects.isNull(response)){
            return new SendMessage(tgChatId, USER_NOT_FOUND_MESSAGE);
        }

        Set<LinkDtoIn> links = response.getLinks();
        if (links.isEmpty()){
            return new SendMessage(tgChatId, EMPTY_SET_MESSAGE);
        }

        StringBuilder message = new StringBuilder("Список отслеживаемых ресурсов:\n");
        for (LinkDtoIn link : links){
            message.append(link.getUrl()).append("\n");
        }


        return new SendMessage(tgChatId, message.toString());
    }
}

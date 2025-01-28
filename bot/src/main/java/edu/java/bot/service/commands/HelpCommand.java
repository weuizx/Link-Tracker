package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HelpCommand implements Command {

    private static final String COMMAND = "/help";
    private static final String DESCRIPTION = "Вывести список доступных команд";

    private final List<Command> commands;

    @Autowired
    public HelpCommand(List<Command> commands) {
        this.commands = commands;
        this.commands.addFirst(this);
    }

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
        String messageText = "Список доступных команд:\n"
            + commands.stream()
            .map(com -> com.command() + " - " + com.description())
            .collect(Collectors.joining("\n"));
        return new SendMessage(update.message().chat().id(), messageText);
    }
}

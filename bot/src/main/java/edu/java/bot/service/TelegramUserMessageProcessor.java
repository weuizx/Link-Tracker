package edu.java.bot.service;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.service.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class TelegramUserMessageProcessor implements UserMessageProcessor {
    private final List<Command> commands;

    @Autowired
    public TelegramUserMessageProcessor(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public List<Command> commands() {
        return commands;
    }

    @Override
    public BotCommand[] commandsForMenu() {
        int size = commands.size();
        BotCommand[] menuCommands = new BotCommand[size];
        for (int i = 0; i < size; i++) {
            menuCommands[i] = commands.get(i).toApiCommand();
        }
        return menuCommands;
    }

    @Override
    public SendMessage process(Update update) {

        for (Command command : commands) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }

        return new SendMessage(update.message().chat().id(), "Неизвестная команда");
    }
}

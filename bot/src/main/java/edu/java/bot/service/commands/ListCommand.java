package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Component;

@Component
public class ListCommand implements Command {
    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "Показать список отслеживаемых ссылок";
    }

    @Override
    public SendMessage handle(Update update) {

        StringBuilder messageText = new StringBuilder();
        if (true) {
            messageText.append("Список отслеживаемых ссылок пуст!");
        } else {
            //вывести список ссылок
        }

        return new SendMessage(update.message().chat().id(), messageText.toString());
    }
}

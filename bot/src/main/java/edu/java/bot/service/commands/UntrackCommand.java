package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.util.UrlChecker;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand implements Command {
    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "Прекратить отслеживание ссылки";
    }

    @Override
    public SendMessage handle(Update update) {

        String messageText = "Введите ссылку";
        String input = update.message().text();
        int spaceIndex = input.indexOf(' ');
        if (spaceIndex != -1) {
            String url = input.substring(spaceIndex + 1);
            if (UrlChecker.isValid(url)) {

                //Проверить, что данная ссылка есть в списке
                //Удаление ссылки из списка
                messageText = "Ссылка успешно удалена из отслеживания: " + url;
            }
        }
        return new SendMessage(update.message().chat().id(), messageText);
    }
}

package edu.java.bot.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.service.UserMessageProcessor;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
public class MyTelegramBot implements Bot {
    private static final Logger log = LoggerFactory.getLogger(MyTelegramBot.class);
    private final UserMessageProcessor messageProcessor;
    private final TelegramBot bot;

    public MyTelegramBot(UserMessageProcessor messageProcessor, ApplicationConfig applicationConfig) {
        this.messageProcessor = messageProcessor;
        this.bot = new TelegramBot(applicationConfig.telegramToken());
        this.bot.execute((new SetMyCommands(messageProcessor.commandsForMenu())));
    }

    @Override
    public <T extends BaseRequest<T, R>, R extends BaseResponse> void execute(BaseRequest<T, R> request) {

        if (request instanceof SendMessage sendMessageRequest) {
            SendResponse sendResponse = bot.execute(sendMessageRequest);
            if (!sendResponse.isOk()) {
                log.info("Сообщение отправлено пользователю " + sendResponse.message().chat().id());
            }
        }
    }

    @Override
    public int process(List<Update> updates) {
        int processedUpdates = 0;
        for (Update update : updates) {
            if (update.message() != null) {
                SendMessage sendMessage = messageProcessor.process(update).parseMode(ParseMode.HTML);
                execute(sendMessage);
                processedUpdates++;
            }
        }
        return processedUpdates;
    }

    @PostConstruct
    @Override
    public void start() {
        bot.setUpdatesListener(updates -> {
            process(updates);
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }, e -> {
            if (e.response() != null) {
                e.response().errorCode();
                e.response().description();
            }
        });
    }

    @Override
    public void close() {

    }
}


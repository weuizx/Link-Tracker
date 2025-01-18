package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class StartCommandTest {
    private final StartCommand startCommand = new StartCommand();
    @Test
    public void toApiCommandTest(){
        BotCommand botCommandMock = Mockito.mock(BotCommand.class);

        when(botCommandMock.command()).thenReturn("/start");
        when(botCommandMock.description()).thenReturn("Начать работу с ботом");

        BotCommand result = startCommand.toApiCommand();

        assertEquals(botCommandMock.command(), result.command());
        assertEquals(botCommandMock.description(), botCommandMock.description());
    }

    @Test
    public void correctCommandSupportsTest(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/start");

        assertTrue(startCommand.supports(updateMock));
    }

    @Test
    public void incorrectCommandSupportsTest(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/weuizx");

        assertFalse(startCommand.supports(updateMock));
    }

    @Test
    public void nullMessageUpdateCommandSupportsTest(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        assertFalse(startCommand.supports(updateMock));
    }

    @Test
    public void nullTextUpdateCommandSupportsTest(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);

        assertFalse(startCommand.supports(updateMock));
    }

}

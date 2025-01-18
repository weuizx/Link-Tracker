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

public class ListCommandTest {
    private final ListCommand listCommand = new ListCommand();
    @Test
    public void toApiCommandTest(){
        BotCommand botCommandMock = Mockito.mock(BotCommand.class);

        when(botCommandMock.command()).thenReturn("/list");
        when(botCommandMock.description()).thenReturn("Показать список отслеживаемых ссылок");

        BotCommand result = listCommand.toApiCommand();

        assertEquals(botCommandMock.command(), result.command());
        assertEquals(botCommandMock.description(), botCommandMock.description());
    }

    @Test
    public void correctCommandSupportsTest(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/list");

        assertTrue(listCommand.supports(updateMock));
    }

    @Test
    public void incorrectCommandSupportsTest(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/weuizx");

        assertFalse(listCommand.supports(updateMock));
    }

    @Test
    public void nullMessageUpdateCommandSupportsTest(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        assertFalse(listCommand.supports(updateMock));
    }

    @Test
    public void nullTextUpdateCommandSupportsTest(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);

        assertFalse(listCommand.supports(updateMock));
    }

}

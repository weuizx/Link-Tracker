package edu.java.bot.service.commands;

import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class UntrackCommandTest {
    private final UntrackCommand untrackCommand = new UntrackCommand();
    @Test
    public void toApiCommandTest(){
        BotCommand botCommandMock = Mockito.mock(BotCommand.class);

        when(botCommandMock.command()).thenReturn("/untrack");
        when(botCommandMock.description()).thenReturn("Прекратить отслеживание ссылки");

        BotCommand result = untrackCommand.toApiCommand();

        assertEquals(botCommandMock.command(), result.command());
        assertEquals(botCommandMock.description(), botCommandMock.description());
    }

    @Test
    public void correctCommandSupportsTest(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/untrack");

        assertTrue(untrackCommand.supports(updateMock));
    }

    @Test
    public void incorrectCommandSupportsTest(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);
        when(messageMock.text()).thenReturn("/weuizx");

        assertFalse(untrackCommand.supports(updateMock));
    }

    @Test
    public void nullMessageUpdateCommandSupportsTest(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        assertFalse(untrackCommand.supports(updateMock));
    }

    @Test
    public void nullTextUpdateCommandSupportsTest(){
        Update updateMock = Mockito.mock(Update.class);
        Message messageMock = Mockito.mock(Message.class);

        when(updateMock.message()).thenReturn(messageMock);

        assertFalse(untrackCommand.supports(updateMock));
    }

}

//package edu.java.bot.service.commands;
//
//import com.pengrad.telegrambot.model.BotCommand;
//import com.pengrad.telegrambot.model.Chat;
//import com.pengrad.telegrambot.model.Message;
//import com.pengrad.telegrambot.model.Update;
//import com.pengrad.telegrambot.request.SendMessage;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//public class TrackCommandTest {
//    private final TrackCommand trackCommand = new TrackCommand();
//    @Test
//    public void toApiCommandTest(){
//        BotCommand botCommandMock = Mockito.mock(BotCommand.class);
//
//        when(botCommandMock.command()).thenReturn("/track");
//        when(botCommandMock.description()).thenReturn("Начать отслеживание ссылки");
//
//        BotCommand result = trackCommand.toApiCommand();
//
//        assertEquals(botCommandMock.command(), result.command());
//        assertEquals(botCommandMock.description(), botCommandMock.description());
//    }
//
//    @Test
//    public void correctCommandSupportsTest(){
//        Update updateMock = Mockito.mock(Update.class);
//        Message messageMock = Mockito.mock(Message.class);
//
//        when(updateMock.message()).thenReturn(messageMock);
//        when(messageMock.text()).thenReturn("/track");
//
//        assertTrue(trackCommand.supports(updateMock));
//    }
//
//    @Test
//    public void incorrectCommandSupportsTest(){
//        Update updateMock = Mockito.mock(Update.class);
//        Message messageMock = Mockito.mock(Message.class);
//
//        when(updateMock.message()).thenReturn(messageMock);
//        when(messageMock.text()).thenReturn("/weuizx");
//
//        assertFalse(trackCommand.supports(updateMock));
//    }
//
//    @Test
//    public void nullMessageUpdateCommandSupportsTest(){
//        Update updateMock = Mockito.mock(Update.class);
//        Message messageMock = Mockito.mock(Message.class);
//
//        assertFalse(trackCommand.supports(updateMock));
//    }
//
//    @Test
//    public void nullTextUpdateCommandSupportsTest(){
//        Update updateMock = Mockito.mock(Update.class);
//        Message messageMock = Mockito.mock(Message.class);
//
//        when(updateMock.message()).thenReturn(messageMock);
//
//        assertFalse(trackCommand.supports(updateMock));
//    }
//}

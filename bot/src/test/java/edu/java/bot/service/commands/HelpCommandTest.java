//package edu.java.bot.service.commands;
//
//import com.pengrad.telegrambot.model.BotCommand;
//import com.pengrad.telegrambot.model.Chat;
//import com.pengrad.telegrambot.model.Message;
//import com.pengrad.telegrambot.model.Update;
//import com.pengrad.telegrambot.request.SendMessage;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import java.util.LinkedList;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//public class HelpCommandTest {
//    private final HelpCommand helpCommand;
//    {
//        List<Command> commands = new LinkedList<>();
//        commands.add(new StartCommand());
//        commands.add(new TrackCommand());
//        commands.add(new UntrackCommand());
//        commands.add(new ListCommand());
//        helpCommand = new HelpCommand(commands);
//    }
//
//    @Test
//    public void toApiCommandTest(){
//        BotCommand botCommandMock = Mockito.mock(BotCommand.class);
//
//        when(botCommandMock.command()).thenReturn("/help");
//        when(botCommandMock.description()).thenReturn("Вывести список доступных команд");
//
//        BotCommand result = helpCommand.toApiCommand();
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
//        when(messageMock.text()).thenReturn("/help");
//
//        assertTrue(helpCommand.supports(updateMock));
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
//        assertFalse(helpCommand.supports(updateMock));
//    }
//
//    @Test
//    public void nullMessageUpdateCommandSupportsTest(){
//        Update updateMock = Mockito.mock(Update.class);
//        Message messageMock = Mockito.mock(Message.class);
//
//        assertFalse(helpCommand.supports(updateMock));
//    }
//
//    @Test
//    public void nullTextUpdateCommandSupportsTest(){
//        Update updateMock = Mockito.mock(Update.class);
//        Message messageMock = Mockito.mock(Message.class);
//
//        when(updateMock.message()).thenReturn(messageMock);
//
//        assertFalse(helpCommand.supports(updateMock));
//    }
//
//    @Test
//    public void handleTest() {
//        Update updateMock = Mockito.mock(Update.class);
//        Message messageMock = Mockito.mock(Message.class);
//        Chat chatMock = Mockito.mock(Chat.class);
//
//        when(updateMock.message()).thenReturn(messageMock);
//        when(messageMock.chat()).thenReturn(chatMock);
//        when(chatMock.id()).thenReturn(1L);
//
//        SendMessage expected = new SendMessage(1L, """
//            Список доступных команд:
//            /help - Вывести список доступных команд
//            /start - Начать работу с ботом
//            /track - Начать отслеживание ссылки
//            /untrack - Прекратить отслеживание ссылки
//            /list - Показать список отслеживаемых ссылок""");
//
//        assertEquals(expected.getParameters().get("chat_id"), helpCommand.handle(updateMock).getParameters().get("chat_id"));
//        assertEquals(expected.getParameters().get("text"), helpCommand.handle(updateMock).getParameters().get("text"));
//    }
//}

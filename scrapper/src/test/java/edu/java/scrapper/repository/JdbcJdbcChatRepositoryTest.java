//package edu.java.scrapper.repository;
//
//import edu.java.scrapper.repositories.jdbc.JdbcChatRepository;
//import edu.java.scrapper.repositories.dto.Chat;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//@SpringBootTest
//public class JdbcJdbcChatRepositoryTest extends IntegrationTest{
//    @Autowired
//    JdbcChatRepository jdbcChatRepository;
//
//    @Test
//    @Transactional
//    @Rollback
//    public void findAllTest(){
//        Chat chat1 = new Chat(999, 69);
//        Chat chat2 = new Chat(998, 36);
//        List<Chat> expected = List.of(chat1, chat2);
//
//        List<Chat> findAllResult = jdbcChatRepository.findAll();
//
//        for(int i = 0; i < findAllResult.size(); i ++){
//            assertEquals(expected.get(i), findAllResult.get(i));
//        }
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void findByTgChatIdTest1(){
//        Chat expected = new Chat(999, 69);
//
//        Chat findByTgChatIdResult = jdbcChatRepository.findByTgChatId(69);
//
//        assertEquals(expected, findByTgChatIdResult);
//    }
//    @Test
//    @Transactional
//    @Rollback
//    public void findByTgChatIdTest2(){
//
//        Chat findByTgChatIdResult = jdbcChatRepository.findByTgChatId(100);
//
//        assertNull(findByTgChatIdResult);
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void addTest1(){
//        Chat chat1 = new Chat(999, 69);
//        Chat chat2 = new Chat(998, 36);
//        Chat chat3 = new Chat(1, 92);
//        List<Chat> expected = List.of(chat1, chat2, chat3);
//
//        jdbcChatRepository.add(92);
//        List<Chat> findAllResult = jdbcChatRepository.findAll();
//
//        for(int i = 0; i < findAllResult.size(); i ++){
//            assertEquals(expected.get(i), findAllResult.get(i));
//        }
//    }
//    @Test
//    @Transactional
//    @Rollback
//    public void addTest2(){
//        Chat chat1 = new Chat(999, 69);
//        Chat chat2 = new Chat(998, 36);
//        List<Chat> expected = List.of(chat1, chat2);
//
//        jdbcChatRepository.add(69);
//        List<Chat> findAllResult = jdbcChatRepository.findAll();
//
//        for(int i = 0; i < findAllResult.size(); i ++){
//            assertEquals(expected.get(i), findAllResult.get(i));
//        }
//    }
//
//    @Test
//    @Transactional
//    @Rollback
//    public void removeTest1(){
//        Chat chat1 = new Chat(999, 69);
//        List<Chat> expected = List.of(chat1);
//
//        jdbcChatRepository.remove(36);
//        List<Chat> findAllResult = jdbcChatRepository.findAll();
//
//        for(int i = 0; i < findAllResult.size(); i ++){
//            assertEquals(expected.get(i), findAllResult.get(i));
//        }
//    }
//    @Test
//    @Transactional
//    @Rollback
//    public void removeTest2(){
//        Chat chat1 = new Chat(999, 69);
//        Chat chat2 = new Chat(998, 36);
//        List<Chat> expected = List.of(chat1, chat2);
//
//        jdbcChatRepository.remove(10);
//        List<Chat> findAllResult = jdbcChatRepository.findAll();
//
//        for(int i = 0; i < findAllResult.size(); i ++){
//            assertEquals(expected.get(i), findAllResult.get(i));
//        }
//    }
//
//}

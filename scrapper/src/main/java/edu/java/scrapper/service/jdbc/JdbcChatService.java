package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.repositories.jdbc.JdbcChatRepository;
import edu.java.scrapper.service.ChatService;
import edu.java.scrapper.exceptions.ChatExistsException;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JdbcChatService implements ChatService {
    private final JdbcChatRepository jdbcChatRepository;

    @Override
    public void register(long tgChatId) throws ChatExistsException {
        if(jdbcChatRepository.add(tgChatId) == null){
            throw new ChatExistsException("Чат уже зарегистрирован");
        }
    }

    @Override
    public void unregister(long tgChatId) throws ChatNotFoundException {
        if(jdbcChatRepository.remove(tgChatId) == 0){
            throw new ChatNotFoundException("Чат не зарегистрирован");
        }
    }
}

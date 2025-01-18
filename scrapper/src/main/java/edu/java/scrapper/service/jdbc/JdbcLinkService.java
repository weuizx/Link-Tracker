package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.repositories.jdbc.JdbcChatRepository;
import edu.java.scrapper.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.repositories.dto.Chat;
import edu.java.scrapper.repositories.dto.Link;
import edu.java.scrapper.service.LinkService;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.LinkExistsException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class JdbcLinkService implements LinkService {

    private final JdbcLinkRepository jdbcLinkRepository;
    private final JdbcChatRepository jdbcChatRepository;
    @Override
    public Link add(long tgChatId, URI url) throws ChatNotFoundException, LinkExistsException {
        Chat chat = jdbcChatRepository.findByTgChatId(tgChatId);
        if(chat == null){
            throw new ChatNotFoundException("Чат не зарегистрирован");
        }
        Link link = jdbcLinkRepository.add(chat.id(), url.toString());
        if(link == null){
            throw new LinkExistsException("Ссылка уже добавлена");
        }
        return link;
    }

    @Override
    public Link remove(long tgChatId, URI url) throws ChatNotFoundException, LinkNotFoundException {
        Chat chat = jdbcChatRepository.findByTgChatId(tgChatId);
        if(chat == null){
            throw new ChatNotFoundException("Чат не зарегистрирован");
        }
        Link link = jdbcLinkRepository.remove(chat.id(), url.toString());
        if(link == null){
            throw new LinkNotFoundException("Ссылка не найдена");
        }
        return link;
    }

    @Override
    public Collection<Link> listAll(long tgChatId) throws ChatNotFoundException {
        Chat chat = jdbcChatRepository.findByTgChatId(tgChatId);
        if(chat == null){
            throw new ChatNotFoundException("Чат не зарегистрирован");
        }
        return jdbcLinkRepository.findAllForId(chat.id());
    }
}

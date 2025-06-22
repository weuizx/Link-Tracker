package edu.java.scrapper.service.jpa;

import edu.java.scrapper.exceptions.ChatExistsException;
import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.LinkExistsException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import edu.java.scrapper.repositories.jpa.ClientRepository;
import edu.java.scrapper.repositories.jpa.LinkRepository;
import edu.java.scrapper.repositories.jpa.entity.Client;
import edu.java.scrapper.repositories.jpa.entity.Link;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final ClientRepository clientRepository;
    private final LinkRepository linkRepository;

    public Client register(long tgChatId) throws ChatExistsException {
        Optional<Client> possibleUser = clientRepository.findByTgChatId(tgChatId);
        if (possibleUser.isPresent()) {
            throw new ChatExistsException("Чат уже зарегистрирован");
        }

        Client client = new Client();
        client.setTgChatId(tgChatId);
        client = clientRepository.save(client);

        return client;

    }

    public void unregister(long tgChatId) throws ChatNotFoundException {

        Client client = getUserByTgChatId(tgChatId);

        Set<Link> links = client.getLinks();

        client.setLinks(Collections.emptySet());
        clientRepository.delete(client);

        for (Link link : links) {
            if (link.getClients().isEmpty()) {
                linkRepository.delete(link);
            }
        }

    }

    public Client addLinkForUser(long tgChatId, String url) throws ChatNotFoundException, LinkExistsException {

        //TODO validate url

        Client client = getUserByTgChatId(tgChatId);

        Optional<Link> possibleLink = linkRepository.findByUrl(url);
        Link link;
        if (possibleLink.isEmpty()) {
            link = new Link();
            link.setUrl(url);
            link.setLastCheckDatetime(ZonedDateTime.now());
            link = linkRepository.save(link);
        } else {
            link = possibleLink.get();
        }

        if (!client.getLinks().add(link)) {
            throw new LinkExistsException("Link already tracking for this user");
        }
        clientRepository.save(client);

        return client;
    }

    public Client removeLinkForUser(long tgChatId, String url) throws ChatNotFoundException, LinkNotFoundException {

        Client client = getUserByTgChatId(tgChatId);

        Optional<Link> possibleLink = linkRepository.findByUrl(url);
        Link link = linkRepository.findByUrl(url)
            .orElseThrow(() -> new LinkNotFoundException("Link not found"));

        if (!client.getLinks().remove(link)) {
            throw new LinkNotFoundException("Link don't tracking for this user");
        }

        client = clientRepository.save(client);

        // Проверяем связь через промежуточную таблицу
        boolean isLinkUsed = clientRepository.existsByLinksContaining(link);
        if (!isLinkUsed) {
            linkRepository.delete(link);
        }

        return client;
    }

    public Client getUserInfo(long tgChatId) throws ChatNotFoundException {
        return getUserByTgChatId(tgChatId);
    }

    private Client getUserByTgChatId(long tgChatId) throws ChatNotFoundException {

        Optional<Client> possibleUser = clientRepository.findByTgChatId(tgChatId);
        if (possibleUser.isEmpty()) {
            throw new ChatNotFoundException("Чат не зарегистрирован");
        }

        return possibleUser.get();
    }

}

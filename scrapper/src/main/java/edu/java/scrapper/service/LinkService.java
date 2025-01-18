package edu.java.scrapper.service;

import edu.java.scrapper.exceptions.ChatNotFoundException;
import edu.java.scrapper.exceptions.LinkExistsException;
import edu.java.scrapper.exceptions.LinkNotFoundException;
import edu.java.scrapper.repositories.dto.Link;
import java.net.URI;
import java.util.Collection;

public interface LinkService {
    Link add(long tgChatId, URI url) throws ChatNotFoundException, LinkExistsException;
    Link remove(long tgChatId, URI url) throws ChatNotFoundException, LinkNotFoundException;
    Collection<Link> listAll(long tgChatId) throws ChatNotFoundException;
}


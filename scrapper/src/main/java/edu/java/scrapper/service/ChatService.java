package edu.java.scrapper.service;

import edu.java.scrapper.exceptions.ChatExistsException;
import edu.java.scrapper.exceptions.ChatNotFoundException;

public interface ChatService {
    void register(long tgChatId) throws ChatExistsException;
    void unregister(long tgChatId) throws ChatNotFoundException;

}

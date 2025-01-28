package edu.java.bot.client;

import edu.java.bot.client.dto.ClientDtoIn;
import edu.java.bot.client.dto.LinkDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ScrapperClient {

    ClientDtoIn registerChat(@PathVariable("id") long tgChatId);

    void deleteChat(@PathVariable("id") long tgChatId);

    ClientDtoIn getUserInfo(@PathVariable("id") long tgChatId);

    ClientDtoIn addLink(@PathVariable("id") long tgChatId, @RequestBody LinkDto linkDto);

    ClientDtoIn removeLink(@PathVariable("id") long tgChatId, @RequestBody LinkDto linkDto);
}

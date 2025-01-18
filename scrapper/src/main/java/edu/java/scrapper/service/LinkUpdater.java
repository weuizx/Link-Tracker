package edu.java.scrapper.service;

import edu.java.scrapper.repositories.dto.Link;
import java.util.List;

public interface LinkUpdater {
    int update(long linkId);

    List<Link> listAllOldCheckedLinks();

    List<Long> listAllTgChatIdByLinkId(Long linkId);
}

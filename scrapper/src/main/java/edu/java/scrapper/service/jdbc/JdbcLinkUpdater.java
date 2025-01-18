package edu.java.scrapper.service.jdbc;

import edu.java.scrapper.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.repositories.dto.Link;
import edu.java.scrapper.service.LinkUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JdbcLinkUpdater implements LinkUpdater {
    private final JdbcLinkRepository jdbcLinkRepository;

    @Override
    public int update(long linkId) {
        return jdbcLinkRepository.updateLastCheckTimeById(linkId);
    }

    @Override
    public List<Link> listAllOldCheckedLinks() {
        return jdbcLinkRepository.findOldCheckedLinks();
    }

    @Override
    public List<Long> listAllTgChatIdByLinkId(Long linkId) {
        return jdbcLinkRepository.findAllTgChatIdByLinkId(linkId);
    }
}

package edu.java.scrapper.repositories.jdbc;

import edu.java.scrapper.repositories.dto.Link;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JdbcLinkRepository {
    private final JdbcTemplate template;
    private final LinkRowMapper linkRowMapper;

    public Link findByUrl(String url) {
        String query = "SELECT id, url, last_check_time, answers_count, commits_count FROM link WHERE url = ?";

        List<Link> result = template.query(query, linkRowMapper, url);
        if (result.isEmpty()) {
            return null;
        } else {
            return result.getFirst();
        }
    }

    @Transactional
    public Link add(long tgChatId, String url) {
        Link link = findByUrl(url);
        if (link == null) {
            String query = "INSERT INTO link (id, url, last_check_time) VALUES (default, ?, default)";
            template.update(query, url);
            link = findByUrl(url);
        }

        String query = "SELECT COUNT(*) FROM relation WHERE tg_chat_id = ? AND link_id = ?";
        long linkId = link.id();
        Integer count = template.queryForObject(query, Integer.class, tgChatId, linkId);

        if (count == null || count == 0) {
            query = "INSERT INTO relation (id, tg_chat_id, link_id) VALUES (default, ?, ?)";
            template.update(query, tgChatId, linkId);
        } else {
            return null;
        }

        return link;
    }

    @Transactional
    public Link remove(long tgChatId, String url) {
        Link link = findByUrl(url);
        if (link == null) {
            return null;
        }
        String query = "DELETE FROM relation WHERE tg_chat_id = ? AND link_id = ?";
        if (template.update(query, tgChatId, link.id()) == 0) {
            return null;
        }
        query = "SELECT COUNT(*) FROM relation WHERE link_id = ?";
        Integer result = template.queryForObject(query, Integer.class, link.id());
        if (result == null || result == 0) {
            query = "DELETE FROM link WHERE url = ?";
            template.update(query, url);
        }

        return link;
    }

    public List<Link> findAll() {
        String query = "SELECT * FROM link";
        return template.query(query, linkRowMapper);
    }

    public List<Link> findAllForId(long tgChatId) {
        String query = """
            SELECT link.*
            FROM relation
            JOIN link ON link.id = relation.link_id
            WHERE relation.tg_chat_id = ?""";
        return template.query(query, linkRowMapper, tgChatId);
    }

    public List<Link> findOldCheckedLinks() {
        String query = """
            SELECT * FROM link
            WHERE EXTRACT(EPOCH FROM (now() - last_check_time)) / 60 > 10""";
        return template.query(query, linkRowMapper);
    }

    @Transactional
    public int updateLastCheckTimeById(long linkId) {
        String query = "UPDATE link SET last_check_time = now() WHERE id = ?";
        return template.update(query, linkId);
    }

    public List<Long> findAllTgChatIdByLinkId(Long linkId) {
        String sql = """
            SELECT chat.tg_chat_id
            FROM relation
            JOIN link ON link.id = relation.link_id
            JOIN chat ON chat.id = relation.tg_chat_id
            WHERE link.id = ?""";
        return template.query(sql, (rs, number) -> rs.getLong("tg_chat_id"), linkId);
    }
}

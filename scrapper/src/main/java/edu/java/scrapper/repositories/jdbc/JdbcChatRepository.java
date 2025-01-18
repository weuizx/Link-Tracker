package edu.java.scrapper.repositories.jdbc;

import edu.java.scrapper.repositories.dto.Chat;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcChatRepository {
    private final JdbcTemplate template;
    private final ChatRowMapper chatRowMapper;

    @Transactional
    public Chat add(long tgChatId){
        String query = "SELECT COUNT(*) FROM chat WHERE tg_chat_id = ?";
        Integer count = template.queryForObject(query, Integer.class, tgChatId);
        if (count == null || count == 0) {
            query = "INSERT INTO chat VALUES (default, ?)";
            template.update(query, tgChatId);
            return findByTgChatId(tgChatId);
        }else{
            return null;
        }
    }


    @Transactional
    public int remove(long tgChatId){
        String deleteQuery = "DELETE FROM chat WHERE tg_chat_id = ?";
        return template.update(deleteQuery, tgChatId);
    }

    public List<Chat> findAll(){
        String selectAllQuery = "SELECT * FROM chat";
        return template.query(selectAllQuery, chatRowMapper);
    }

    public Chat findByTgChatId(long tgChatId) {
        String query = "SELECT * FROM chat WHERE tg_chat_id = ?";

        List<Chat> result = template.query(query, chatRowMapper, tgChatId);
        if (result.isEmpty()) {
            return null;
        } else {
            return result.getFirst();
        }
    }

}

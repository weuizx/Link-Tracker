package edu.java.scrapper.repositories.jdbc;

import edu.java.scrapper.repositories.dto.Chat;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ChatRowMapper implements RowMapper<Chat> {
    @Override
    public Chat mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Chat(
            rs.getLong("id"),
            rs.getLong("tg_chat_id")
        );
    }
}

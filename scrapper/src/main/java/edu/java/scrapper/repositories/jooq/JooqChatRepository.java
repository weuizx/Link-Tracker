package edu.java.scrapper.repositories.jooq;

import edu.java.scrapper.repositories.dto.Chat;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.scrapper.repositories.jooq.Tables.CHAT;

@Repository
public class JooqChatRepository {

    private final DSLContext dslContext;

    public JooqChatRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<Chat> findAll() {
        return dslContext.selectFrom(CHAT)
            .fetch()
            .into(Chat.class);
    }

    public Long getChatIdByTgChatId(Long tgChatId) {
        return dslContext.select(CHAT.ID)
            .from(CHAT)
            .where(CHAT.TG_CHAT_ID.eq(tgChatId))
            .fetchOneInto(Long.class);
    }

    @Transactional
    public Chat add(Long tgChatId) {
        return dslContext.insertInto(CHAT, CHAT.TG_CHAT_ID)
            .values(tgChatId)
            .returning()
            .fetchOneInto(Chat.class);
    }

    @Transactional
    public int remove(Long tgChatId) {
        return dslContext.deleteFrom(CHAT)
            .where(CHAT.TG_CHAT_ID.eq(tgChatId))
            .execute();
    }

}

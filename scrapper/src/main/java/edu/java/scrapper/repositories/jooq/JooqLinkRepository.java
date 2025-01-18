package edu.java.scrapper.repositories.jooq;

import edu.java.scrapper.repositories.dto.Link;
import java.time.OffsetDateTime;
import java.util.List;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.scrapper.repositories.jooq.Tables.CHAT;
import static edu.java.scrapper.repositories.jooq.Tables.RELATION;
import static edu.java.scrapper.repositories.jooq.Tables.LINK;

@Repository
public class JooqLinkRepository {

    private final DSLContext dslContext;

    private final LinkRecordMapper linkRecordMapper;

    public JooqLinkRepository(DSLContext dslContext, LinkRecordMapper linkRecordMapper) {
        this.dslContext = dslContext;
        this.linkRecordMapper = linkRecordMapper;
    }

    public List<Link> findAll() {
        return dslContext.selectFrom(LINK)
            .fetchInto(Link.class);
    }

    public List<Link> findAllByChatId(Long chatId) {
        return dslContext.select(LINK.asterisk())
            .from(LINK)
            .join(RELATION).on(RELATION.LINK_ID.eq(LINK.ID))
            .where(RELATION.CHAT_ID.eq(chatId))
            .fetchInto(Link.class);
    }

    private Long getLinkIdByUrl(String url) {
        return dslContext.select(LINK.ID)
            .from(LINK)
            .where(LINK.URL.eq(url))
            .fetchOneInto(Long.class);
    }

    private Link getLinkByUrl(String url) {
        return dslContext.selectFrom(LINK)
            .where(LINK.URL.eq(url))
            .fetchOneInto(Link.class);
    }

    @Transactional
    public Link add(Long chatId, String url) {
        Long linKId = getLinkIdByUrl(url);
        if (linKId == null) {
            linKId = dslContext.insertInto(LINK, LINK.URL)
                .values(url)
                .returning(LINK.ID)
                .fetchOne()
                .getId();
        }
        return dslContext.insertInto(RELATION, RELATION.CHAT_ID, RELATION.LINK_ID)
            .values(chatId, linKId)
            .returning()
            .fetch()
            .map(linkRecordMapper::map)
            .getFirst();
    }

    @Transactional
    public Link remove(Long chatId, String url) {
        Long linkId = getLinkIdByUrl(url);
        if (linkId == null) {
            return null;
        }

        Link link = getLinkByUrl(url);
        boolean res = dslContext.deleteFrom(RELATION)
            .where(RELATION.CHAT_ID.eq(chatId))
            .and(RELATION.LINK_ID.eq(linkId))
            .execute() == 0;
        if (res) {
            return null;
        }

        List<Long> ids = dslContext.select(RELATION.ID)
            .from(RELATION)
            .where(RELATION.LINK_ID.eq(linkId))
            .fetchInto(Long.class);
        if (ids.isEmpty()) {
            dslContext.deleteFrom(LINK)
                .where(LINK.ID.eq(linkId))
                .execute();
        }
        return link;
    }

    public List<Link> findOldCheckedLINK(Long forceCheckDelay) {
        return dslContext.selectFrom(LINK)
            .where(LINK.LAST_CHECK_TIME.lt(OffsetDateTime.now().minusSeconds(forceCheckDelay)))
            .fetchInto(Link.class);
    }

    @Transactional
    public int updateLastCheckTime(Long linkId) {
        return dslContext.update(LINK)
            .set(LINK.LAST_CHECK_TIME, OffsetDateTime.now())
            .where(LINK.ID.eq(linkId))
            .execute();
    }

    public List<Long> findAllTgChatIdByLinkId(Long linkId) {
        return dslContext.select(CHAT.TG_CHAT_ID)
            .from(RELATION)
            .join(CHAT).on(CHAT.ID.eq(RELATION.CHAT_ID))
            .where(RELATION.LINK_ID.eq(linkId))
            .fetchInto(Long.class);
    }
}

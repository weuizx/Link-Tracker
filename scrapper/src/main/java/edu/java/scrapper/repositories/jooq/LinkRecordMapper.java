package edu.java.scrapper.repositories.jooq;


import edu.java.scrapper.repositories.dto.Link;
import edu.java.scrapper.repositories.jooq.tables.records.RelationRecord;
import edu.java.scrapper.repositories.jooq.tables.records.LinkRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;
import static edu.java.scrapper.repositories.jooq.Tables.RELATION;
import static edu.java.scrapper.repositories.jooq.Tables.LINK;

@RequiredArgsConstructor
@Component
public class LinkRecordMapper implements RecordMapper<LinkRecord, Link> {

    private final DSLContext dslContext;

    @Override
    public Link map(LinkRecord r) {
        return new Link(
            r.get(LINK.ID),
            r.get(LINK.URL),
            r.get(LINK.LAST_CHECK_TIME),
            r.get(LINK.ANSWERS_COUNT),
            r.get(LINK.COMMITS_COUNT)
        );
    }

    public Link map(RelationRecord r) {
        Long linkId = r.get(RELATION.LINK_ID);
        return dslContext.selectFrom(LINK)
            .where(LINK.ID.eq(linkId))
            .fetch()
            .map(this)
            .getFirst();
    }
}

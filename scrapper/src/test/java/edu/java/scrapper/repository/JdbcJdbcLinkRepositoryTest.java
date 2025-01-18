package edu.java.scrapper.repository;

import edu.java.scrapper.repositories.jdbc.JdbcLinkRepository;
import edu.java.scrapper.repositories.dto.Link;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class JdbcJdbcLinkRepositoryTest extends IntegrationTest {
    @Autowired
    JdbcLinkRepository jdbcLinkRepository;

    @Test
    public void findAllTest() {
        Link link1 = new Link(999, "https://github.com/weuizx/RosatomApp", OffsetDateTime.now(), 0, 0);
        Link link2 = new Link(998, "https://stackoverflow.com/questions/78797605", OffsetDateTime.now(),0, 0);
        List<Link> expected = List.of(link1, link2);

        List<Link> findAllResult = jdbcLinkRepository.findAll();

        for (int i = 0; i < findAllResult.size(); i++) {
            assertEquals(expected.get(i).id(), findAllResult.get(i).id());
            assertEquals(expected.get(i).url(), findAllResult.get(i).url());
        }
    }

    @Test
    public void findAllForIdTest1() {
        Link link1 = new Link(999, "https://github.com/weuizx/RosatomApp", OffsetDateTime.now(), 0, 0);
        List<Link> expected = List.of(link1);

        List<Link> findAllResult = jdbcLinkRepository.findAllForId(999);

        for (int i = 0; i < findAllResult.size(); i++) {
            assertEquals(expected.get(i).id(), findAllResult.get(i).id());
            assertEquals(expected.get(i).url(), findAllResult.get(i).url());
        }
    }

    @Test
    public void findAllForIdTest2() {
        List<Link> findAllResult = jdbcLinkRepository.findAllForId(997);

        assertTrue(findAllResult.isEmpty());
    }

    @Test
    public void findByUrlTest1() {
        Link expected = new Link(999, "https://github.com/weuizx/RosatomApp", OffsetDateTime.now(), 0, 0);

        Link findAllResult = jdbcLinkRepository.findByUrl("https://github.com/weuizx/RosatomApp");

        assertEquals(expected.id(), findAllResult.id());
        assertEquals(expected.url(), findAllResult.url());
    }
    @Test
    public void findByUrlTest2() {
        Link findAllResult = jdbcLinkRepository.findByUrl("https://github.com/weuizx/example");

        assertNull(findAllResult);
    }

    @Test
    @Transactional
    @Rollback
    public void addTest1(){
        Link link1 = new Link(999, "https://github.com/weuizx/RosatomApp", OffsetDateTime.now(), 0, 0);
        Link link2 = new Link(1, "https://github.com/weuizx/Java", OffsetDateTime.now(), 0, 0);
        List<Link> expected = List.of(link1, link2);

        jdbcLinkRepository.add(999,"https://github.com/weuizx/Java");
        List<Link> findAllResult = jdbcLinkRepository.findAllForId(999);

        for (int i = 0; i < findAllResult.size(); i++) {
            assertEquals(expected.get(i).id(), findAllResult.get(i).id());
            assertEquals(expected.get(i).url(), findAllResult.get(i).url());
        }
    }
    @Test
    @Transactional
    @Rollback
    public void addTest2(){
        Link link1 = new Link(999, "https://github.com/weuizx/RosatomApp", OffsetDateTime.now(), 0, 0);
        Link link2 = new Link(998, "https://stackoverflow.com/questions/78797605", OffsetDateTime.now(), 0, 0);
        List<Link> expected = List.of(link1, link2);

        jdbcLinkRepository.add(999,"https://stackoverflow.com/questions/78797605");
        List<Link> findAllResult = jdbcLinkRepository.findAllForId(999);

        for (int i = 0; i < findAllResult.size(); i++) {
            assertEquals(expected.get(i).id(), findAllResult.get(i).id());
            assertEquals(expected.get(i).url(), findAllResult.get(i).url());
        }
    }
    @Test
    @Transactional
    @Rollback
    public void addTest3(){
        Link link1 = new Link(999, "https://github.com/weuizx/RosatomApp", OffsetDateTime.now(), 0, 0);
        List<Link> expected = List.of(link1);

        jdbcLinkRepository.add(999,"https://github.com/weuizx/RosatomApp");
        List<Link> findAllResult = jdbcLinkRepository.findAllForId(999);

        for (int i = 0; i < findAllResult.size(); i++) {
            assertEquals(expected.get(i).id(), findAllResult.get(i).id());
            assertEquals(expected.get(i).url(), findAllResult.get(i).url());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest1(){
        Link link1 = new Link(999, "https://github.com/weuizx/RosatomApp", OffsetDateTime.now(), 0, 0);
        Link link2 = new Link(998, "https://stackoverflow.com/questions/78797605", OffsetDateTime.now(), 0, 0);
        List<Link> expected = List.of(link1, link2);

        jdbcLinkRepository.add(999,"https://stackoverflow.com/questions/78797605");
        jdbcLinkRepository.remove(998, "https://stackoverflow.com/questions/78797605");
        List<Link> findAllResult = jdbcLinkRepository.findAll();
        List<Link> findAllForIdResult = jdbcLinkRepository.findAllForId(999);

        for (int i = 0; i < findAllResult.size(); i++) {
            assertEquals(expected.get(i).id(), findAllResult.get(i).id());
            assertEquals(expected.get(i).url(), findAllResult.get(i).url());
        }
        for (int i = 0; i < findAllForIdResult.size(); i++) {
            assertEquals(expected.get(i).id(), findAllForIdResult.get(i).id());
            assertEquals(expected.get(i).url(), findAllForIdResult.get(i).url());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest2(){
        Link link1 = new Link(999, "https://github.com/weuizx/RosatomApp", OffsetDateTime.now(), 0, 0);
        List<Link> expected = List.of(link1);

        jdbcLinkRepository.remove(998, "https://stackoverflow.com/questions/78797605");
        List<Link> findAllResult = jdbcLinkRepository.findAll();
        List<Link> findAllForIdResult = jdbcLinkRepository.findAllForId(999);

        for (int i = 0; i < findAllResult.size(); i++) {
            assertEquals(expected.get(i).id(), findAllResult.get(i).id());
            assertEquals(expected.get(i).url(), findAllResult.get(i).url());
        }
        for (int i = 0; i < findAllForIdResult.size(); i++) {
            assertEquals(expected.get(i).id(), findAllForIdResult.get(i).id());
            assertEquals(expected.get(i).url(), findAllForIdResult.get(i).url());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest3(){
        Link link1 = new Link(999, "https://github.com/weuizx/RosatomApp", OffsetDateTime.now(), 0, 0);
        Link link2 = new Link(998, "https://stackoverflow.com/questions/78797605", OffsetDateTime.now(), 0, 0);
        List<Link> expected = List.of(link1, link2);

        jdbcLinkRepository.remove(998, "https://github.com/weuizx/Java");
        List<Link> findAllResult = jdbcLinkRepository.findAll();

        for (int i = 0; i < findAllResult.size(); i++) {
            assertEquals(expected.get(i).id(), findAllResult.get(i).id());
            assertEquals(expected.get(i).url(), findAllResult.get(i).url());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest4(){
        Link link1 = new Link(999, "https://github.com/weuizx/RosatomApp", OffsetDateTime.now(), 0, 0);
        Link link2 = new Link(998, "https://stackoverflow.com/questions/78797605", OffsetDateTime.now(), 0, 0);
        List<Link> expected = List.of(link1, link2);

        jdbcLinkRepository.remove(100, "https://github.com/weuizx/RosatomApp");
        List<Link> findAllResult = jdbcLinkRepository.findAll();

        for (int i = 0; i < findAllResult.size(); i++) {
            assertEquals(expected.get(i).id(), findAllResult.get(i).id());
            assertEquals(expected.get(i).url(), findAllResult.get(i).url());
        }
    }


}

package edu.java.scrapper.repositories.jpa;

import edu.java.scrapper.repositories.jpa.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByUrl(String url);

    List<Link> findAllByLastCheckDatetimeBefore(ZonedDateTime zonedDateTime);
}

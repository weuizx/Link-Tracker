package edu.java.scrapper.repositories.jpa;

import edu.java.scrapper.repositories.jpa.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByTgChatId(Long tgChatId);
}

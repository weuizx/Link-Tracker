package edu.java.scrapper.repositories.jpa;

import edu.java.scrapper.repositories.jpa.entity.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByTgChatId(Long tgChatId);
}

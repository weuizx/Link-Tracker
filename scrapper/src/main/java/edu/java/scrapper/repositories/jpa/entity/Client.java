package edu.java.scrapper.repositories.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(
    name = "client"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "client_seq")
    @SequenceGenerator(
        name = "client_seq",
        sequenceName = "client_id_seq",
        allocationSize = 1
    )
    Long id;

    @Column(
        name = "tg_chat_id",
        columnDefinition = "TIMESTAMP WITH TIME ZONE DEFAULT NOW()",
        nullable = false
    )
    Long tgChatId;

    @ManyToMany(
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "link_to_client",
        joinColumns = {@JoinColumn(
            name = "client_id"
        )},
        inverseJoinColumns = {@JoinColumn(
            name = "link_id"
        )}
    )
    Set<Link> links = new HashSet<>();
}

package edu.java.scrapper.repositories.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(
    name = "link"
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Link {

    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "link_seq")
    @SequenceGenerator(
        name = "link_seq",
        sequenceName = "link_id_seq",
        allocationSize = 1)
    Long id;

    @Column(name = "url")
    String url;

    @Column(
        name = "last_check_datetime",
        nullable = false
    )
    ZonedDateTime lastCheckDatetime;

    @Column(
        name = "videos_count"
    )
    Long videosCount;

    @ToString.Exclude
    @ManyToMany(
        mappedBy = "links",
        fetch = FetchType.EAGER
    )
    Set<Client> clients = new HashSet<>();

}

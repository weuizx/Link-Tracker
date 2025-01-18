package edu.java.scrapper.repositories.dto;

import java.time.OffsetDateTime;

public record Link(
    long id,
    String url,
    OffsetDateTime lastCheckTime,
    long answersCount,
    long commitsCount
) {
}

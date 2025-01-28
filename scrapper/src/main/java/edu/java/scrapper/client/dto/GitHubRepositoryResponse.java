package edu.java.scrapper.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

public record GitHubRepositoryResponse(
    long id,
    String name,
    @JsonProperty("pushed_at")
    ZonedDateTime pushedAt,
    @JsonProperty("updated_at")
    ZonedDateTime updatedAt
) {
}

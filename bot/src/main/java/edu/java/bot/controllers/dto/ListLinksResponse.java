package edu.java.bot.controllers.dto;

import java.util.List;

public record ListLinksResponse(
    List<LinkResponse> links,
    int size
) {
}

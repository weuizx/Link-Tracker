package edu.java.bot.controllers.dto;

import java.net.URI;

public record LinkResponse(
    int id,
    URI url
) {
}

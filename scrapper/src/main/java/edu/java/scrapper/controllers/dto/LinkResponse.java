package edu.java.scrapper.controllers.dto;

import java.net.URI;

public record LinkResponse(
    long id,
    URI url
) {
}

package edu.java.bot.controllers.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

public record UpdateLinksRequest (
    long id,
    @NotNull
    @Pattern(regexp = "^(http|https)://.*$", message = "URL must be a valid link starting with http or https")
    String url,
    String description,
    List<Long> tgChatIds
){
}

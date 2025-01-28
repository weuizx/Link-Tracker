package edu.java.bot.controllers.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record NotifyUsersDtoIn(
    @NotNull
    String url,
    String description,
    List<Long> tgChatIds
){
}

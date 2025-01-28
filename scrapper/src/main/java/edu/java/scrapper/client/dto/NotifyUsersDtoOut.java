package edu.java.scrapper.client.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record NotifyUsersDtoOut(
    @NotNull
    String url,
    String description,
    List<Long> tgChatIds
){
}

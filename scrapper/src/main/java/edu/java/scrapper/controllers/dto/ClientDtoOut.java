package edu.java.scrapper.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Пользователь")
public class ClientDtoOut {

    @Schema(description = "Внутренний уникальный идентификатор пользователя")
    private Long id;

    @Schema(description = "Уникальный идентификатор чата в Telegram API")
    private Long tgChatId;

    @Schema(description = "Отслеживаемые ресурсы для данного пользователя")
    private Set<LinkDtoOut> links;
}

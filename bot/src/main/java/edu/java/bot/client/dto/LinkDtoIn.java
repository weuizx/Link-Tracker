package edu.java.bot.client.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkDtoIn extends LinkDto{

    @Schema(description = "Внутренний уникальный идентификатор интернет-ресурса")
    private Long id;

    @Schema(description = "Время, в которое интернет-ресурс последний раз проверялся на предмет обновлений")
    ZonedDateTime lastCheckDatetime;;
}

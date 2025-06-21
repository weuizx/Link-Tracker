package edu.java.scrapper.controllers.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkDtoOut extends LinkDto {

    @Schema(description = "Внутренний уникальный идентификатор интернет-ресурса")
    private Long id;

    @Schema(description = "Время, в которое интернет-ресурс последний раз проверялся на предмет обновлений")
    ZonedDateTime lastCheckDatetime;
}

package edu.java.bot.client.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Интернет-ресурс")
public class LinkDto {

    @NotNull
    @Pattern(regexp = "^(http|https)://.*$", message = "URL must be a valid link starting with http or https")
    @Schema(description = "URL интернет-ресурса")
    private String url;

}

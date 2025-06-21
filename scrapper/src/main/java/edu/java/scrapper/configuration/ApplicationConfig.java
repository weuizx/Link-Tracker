package edu.java.scrapper.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;

@Validated
@EnableScheduling
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    Scheduler scheduler,
    @NotNull
    BaseUrls urls,
    @NotNull
    @JsonProperty("credentials")
    Credentials credentials
) {
    public record Scheduler(
        boolean enable,
        @NotNull
        Duration interval,
        @NotNull
        Duration forceCheckDelay
    ) {
    }

    public record BaseUrls(
        @NotNull
        String gitHubBaseUrl,
        @NotNull
        String stackOverflowBaseUrl,
        @NotNull
        String youTubeBaseUrl,
        @NotNull
        String botBaseUrl
    ) {
    }

    public record Credentials(
        @NotNull
        String youTubeKey
    ) {
    }
}

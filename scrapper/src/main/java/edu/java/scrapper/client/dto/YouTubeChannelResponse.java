package edu.java.scrapper.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record YouTubeChannelResponse(
    List<YoutubeChannelItem> items
) {
    public record YoutubeChannelItem(
        Snippet snippet,
        @JsonProperty("statistics")
        Statistics statistics
    ) {
        public record Snippet(
            String title
        ) {
        }

        public record Statistics(
            @JsonProperty("videoCount")
            Long videoCount
        ) {
        }
    }
}

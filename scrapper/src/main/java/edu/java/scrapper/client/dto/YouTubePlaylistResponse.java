package edu.java.scrapper.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record YouTubePlaylistResponse(
    List<YoutubePlaylistItem> items
) {
    public record YoutubePlaylistItem(
        Snippet snippet,
        @JsonProperty("contentDetails")
        ContentDetails contentDetails
    ) {
        public record Snippet(
            String title,
            String channelTitle
        ) {
        }

        public record ContentDetails(
            @JsonProperty("itemCount")
            Long itemCount
        ) {
        }
    }
}

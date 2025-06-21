package edu.java.scrapper.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;
import java.util.List;

public record StackOverflowQuestionResponse(
    List<StackOverflowQuestionItem> items
) {
    public record StackOverflowQuestionItem(

        @JsonProperty("question_id")
        Long questionId,

        @JsonProperty("title")
        String title,

        @JsonProperty("is_answered")
        boolean isAnswered,

        @JsonProperty("view_count")
        int viewCount,

        @JsonProperty("answer_count")
        int answerCount,

        @JsonProperty("score")
        int score,

        @JsonProperty("creation_date")
        ZonedDateTime creationDate,

        @JsonProperty("last_activity_date")
        ZonedDateTime lastActivityDate
    ) {
    }
}

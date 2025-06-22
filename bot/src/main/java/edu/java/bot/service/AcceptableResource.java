package edu.java.bot.service;

import java.util.regex.Pattern;
import lombok.Getter;

public enum AcceptableResource {
    YOUTUBE_PLAYLIST(
        "^https?://(www\\.)?youtube\\.com/playlist\\?list=[a-zA-Z_!@#$&-]+.*$",
        "https://www.youtube.com/playlist?list={идентификатор_плейлиста}"
    ),

    YOUTUBE_CHANNEL_VIDEOS(
        "^https?://(www\\.)?youtube\\.com/[a-zA-Z_!@#$&-]+.*$",
        "https://www.youtube.com/{идентификатор_канала}/videos"
    ),

    HABR_ARTICLES(
        "^https?://habr\\.com/ru/users/[a-zA-Z_!@#$&-]+.*$",
        "https://habr.com/ru/users/{идентификатор_пользователя}/articles"
    ),

    GITHUB_REPO(
        "^https?://github\\.com/[a-zA-Z_!@#$&-]+/[a-zA-Z_!@#$&-]+.*$",
        "https://github.com/{идентификатор_пользователя}/{идентификатор_репозитория}"
    ),

    STACKOVERFLOW_QUESTION(
        "^https?://stackoverflow\\.com/questions/\\d+.*$",
        "https://stackoverflow.com/questions/{номер_вопроса}"
    );

    private final Pattern pattern;
    @Getter
    private final String template;

    AcceptableResource(String regex, String template) {
        this.pattern = Pattern.compile(regex);
        this.template = template;
    }

    public boolean matches(String url) {
        return pattern.matcher(url).matches();
    }
}

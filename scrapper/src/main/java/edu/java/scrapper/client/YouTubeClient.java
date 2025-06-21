package edu.java.scrapper.client;

import edu.java.scrapper.client.dto.YouTubeChannelResponse;
import edu.java.scrapper.client.dto.YouTubePlaylistResponse;

public interface YouTubeClient {

    YouTubePlaylistResponse fetchPlaylist(String playlistId);

    YouTubeChannelResponse fetchChannel(String channelId);

}

package edu.java.scrapper.client.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

@JacksonXmlRootElement(localName = "rss")
@JsonIgnoreProperties(ignoreUnknown = true)
public record HabrRssResponse(
    @JacksonXmlProperty(localName = "channel")
    Channel channel
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Channel(
        @JacksonXmlProperty(localName = "item")
        @JacksonXmlElementWrapper(useWrapping = false)
        List<Item> items
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Item(
        @JacksonXmlProperty(localName = "title")
        String title,

        @JacksonXmlProperty(localName = "pubDate")
        String pubDate
    ) {
    }
}

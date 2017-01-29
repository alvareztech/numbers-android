package tech.alvarez.numbers.models.youtube.search;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel Alvarez on 4/8/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class SnippetSearchResponse {

    @SerializedName("title")
    private String title;
    @SerializedName("channelId")
    private String channelId;
    @SerializedName("description")
    private String description;
    @SerializedName("thumbnails")
    private ThumbnailsSearchResponse thumbnails;

    public ThumbnailsSearchResponse getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(ThumbnailsSearchResponse thumbnails) {
        this.thumbnails = thumbnails;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

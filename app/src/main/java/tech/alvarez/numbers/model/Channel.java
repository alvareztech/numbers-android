package tech.alvarez.numbers.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel Alvarez on 8/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class Channel {

    @SerializedName("channelId")
    private String channelId;
    @SerializedName("channelTitle")
    private String channelTitle;
    @SerializedName("description")
    private String description;
    @SerializedName("thumbnails")
    private Thumbnails thumbnails;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Thumbnails getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(Thumbnails thumbnails) {
        this.thumbnails = thumbnails;
    }
}

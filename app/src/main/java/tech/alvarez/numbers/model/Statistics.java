package tech.alvarez.numbers.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel Alvarez on 8/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class Statistics {

    @SerializedName("viewCount")
    private String viewCount;
    @SerializedName("subscriberCount")
    private String subscriberCount;
    @SerializedName("videoCount")
    private String videoCount;

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(String subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public String getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(String videoCount) {
        this.videoCount = videoCount;
    }
}

package tech.alvarez.numbers.model.youtube;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel Alvarez on 24/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class StatisticsResponse {

    @SerializedName("viewCount")
    private String viewCount;
    @SerializedName("subscriberCount")
    private String subscriberCount;
    @SerializedName("videoCount")
    private String videoCount;
    @SerializedName("hiddenSubscriberCount")
    private boolean hiddenSubscriberCount;

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

    public boolean isHiddenSubscriberCount() {
        return hiddenSubscriberCount;
    }

    public void setHiddenSubscriberCount(boolean hiddenSubscriberCount) {
        this.hiddenSubscriberCount = hiddenSubscriberCount;
    }
}

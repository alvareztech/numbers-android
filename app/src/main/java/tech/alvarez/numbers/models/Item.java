package tech.alvarez.numbers.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel Alvarez on 8/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class Item {

    @SerializedName("snippet")
    private Channel channel;
    @SerializedName("statistics")
    private Statistics statistics;

    public Item(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}

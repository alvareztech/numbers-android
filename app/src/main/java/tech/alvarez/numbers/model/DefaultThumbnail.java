package tech.alvarez.numbers.model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by Daniel Alvarez on 8/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class DefaultThumbnail {

    @SerializedName("url")
    private String url;

    public DefaultThumbnail() {
    }

    public DefaultThumbnail(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


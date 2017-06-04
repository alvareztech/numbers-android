package tech.alvarez.numbers.model.youtube;

import com.google.gson.annotations.SerializedName;

import tech.alvarez.numbers.model.DefaultThumbnail;

/**
 * Created by Daniel Alvarez on 30/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class ThumbnailsResponse {

    @SerializedName("default")
    private DefaultThumbnail defaultThumbnail;


    public DefaultThumbnail getDefaultThumbnail() {
        return defaultThumbnail;
    }

    public void setDefaultThumbnail(DefaultThumbnail defaultThumbnail) {
        this.defaultThumbnail = defaultThumbnail;
    }
}

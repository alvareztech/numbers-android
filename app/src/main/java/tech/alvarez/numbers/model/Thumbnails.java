package tech.alvarez.numbers.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel Alvarez on 8/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class Thumbnails {

    @SerializedName("default")
    private DefaultThumbnail defaultThumbnail;

    public DefaultThumbnail getDefaultThumbnail() {
        return defaultThumbnail;
    }

    public void setDefaultThumbnail(DefaultThumbnail defaultThumbnail) {
        this.defaultThumbnail = defaultThumbnail;
    }
}

package tech.alvarez.numbers.models.youtube.search;

import com.google.gson.annotations.SerializedName;

import tech.alvarez.numbers.models.DefaultThumbnail;

/**
 * Created by Daniel Alvarez on 4/8/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class ThumbnailsSearchResponse {

    @SerializedName("default")
    private DefaultThumbnailSearchResponse defaultThumbnail;

    public DefaultThumbnailSearchResponse getDefaultThumbnail() {
        return defaultThumbnail;
    }

    public void setDefaultThumbnail(DefaultThumbnailSearchResponse defaultThumbnail) {
        this.defaultThumbnail = defaultThumbnail;
    }
}

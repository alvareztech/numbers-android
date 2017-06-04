package tech.alvarez.numbers.model.youtube.search;

import com.google.gson.annotations.SerializedName;

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

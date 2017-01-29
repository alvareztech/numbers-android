package tech.alvarez.numbers.models.youtube.search;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel Alvarez on 4/8/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class DefaultThumbnailSearchResponse {

    @SerializedName("url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

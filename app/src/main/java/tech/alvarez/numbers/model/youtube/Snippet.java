package tech.alvarez.numbers.model.youtube;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel Alvarez on 30/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class Snippet {

    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("thumbnails")
    private ThumbnailsResponse thumbnails;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ThumbnailsResponse getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(ThumbnailsResponse thumbnails) {
        this.thumbnails = thumbnails;
    }
}

package tech.alvarez.numbers.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Daniel Alvarez on 7/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class VideoResponse {

    @SerializedName("kind")
    private String kind;
    @SerializedName("etag")
    private String etag;

    @SerializedName("items")
    private List<Item> items;

    public VideoResponse(String kind, String etag) {
        this.kind = kind;
        this.etag = etag;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}

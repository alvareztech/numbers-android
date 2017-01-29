package tech.alvarez.numbers.models.youtube.search;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Daniel Alvarez on 4/8/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class SearchResponse {

    @SerializedName("items")
    private ArrayList<ItemSearchResponse> items;

    public ArrayList<ItemSearchResponse> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemSearchResponse> items) {
        this.items = items;
    }
}

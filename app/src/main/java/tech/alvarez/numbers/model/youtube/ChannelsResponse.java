package tech.alvarez.numbers.model.youtube;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Daniel Alvarez on 24/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class ChannelsResponse {

    @SerializedName("items")
    private ArrayList<ItemResponse> items;


    public ArrayList<ItemResponse> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemResponse> items) {
        this.items = items;
    }


}

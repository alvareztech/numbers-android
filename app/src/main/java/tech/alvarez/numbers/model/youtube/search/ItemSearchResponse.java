package tech.alvarez.numbers.model.youtube.search;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel Alvarez on 4/8/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class ItemSearchResponse {

    @SerializedName("snippet")
    private SnippetSearchResponse snippet;

    public SnippetSearchResponse getSnippet() {
        return snippet;
    }

    public void setSnippet(SnippetSearchResponse snippet) {
        this.snippet = snippet;
    }
}

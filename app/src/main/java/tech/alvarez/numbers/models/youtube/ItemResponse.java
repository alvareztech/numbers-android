package tech.alvarez.numbers.models.youtube;

import com.google.gson.annotations.SerializedName;

import tech.alvarez.numbers.models.Statistics;

/**
 * Created by Daniel Alvarez on 24/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class ItemResponse {

    @SerializedName("id")
    private String id;
    @SerializedName("snippet")
    private Snippet snippet;
    @SerializedName("statistics")
    private StatisticsResponse statistics;
    @SerializedName("brandingSettings")
    private BrandingSettings brandingSettings;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatisticsResponse getStatistics() {
        return statistics;
    }

    public void setStatistics(StatisticsResponse statistics) {
        this.statistics = statistics;
    }

    public BrandingSettings getBrandingSettings() {
        return brandingSettings;
    }

    public void setBrandingSettings(BrandingSettings brandingSettings) {
        this.brandingSettings = brandingSettings;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }
}


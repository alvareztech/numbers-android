package tech.alvarez.numbers.model.youtube;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel Alvarez on 30/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class BannerImage {

    @SerializedName("bannerImageUrl")
    private String bannerImageUrl;
    @SerializedName("bannerMobileImageUrl")
    private String bannerMobileImageUrl;

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }


    public String getBannerMobileImageUrl() {
        return bannerMobileImageUrl;
    }

    public void setBannerMobileImageUrl(String bannerMobileImageUrl) {
        this.bannerMobileImageUrl = bannerMobileImageUrl;
    }
}

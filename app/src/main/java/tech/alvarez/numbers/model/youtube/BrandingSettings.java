package tech.alvarez.numbers.model.youtube;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Daniel Alvarez on 30/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class BrandingSettings {

    @SerializedName("image")
    private BannerImage image;

    public BannerImage getImage() {
        return image;
    }

    public void setImage(BannerImage image) {
        this.image = image;
    }
}

package tech.alvarez.numbers.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import tech.alvarez.numbers.db.converter.DateConverter;

@Entity(tableName = "channels")
@TypeConverters(DateConverter.class)
public class ChannelEntity {

    @PrimaryKey
    private String id;
    private String name;
    private String description;
    @ColumnInfo(name = "profile_url")
    private String profileUrl;
    @ColumnInfo(name = "banner_url")
    private String bannerUrl;
    private int subscribers;
    @ColumnInfo(name = "hidden_subscribers")
    private boolean hiddenSubscribers;
    private long views;
    private long videos;
    private Date update;
    private boolean favorite;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public boolean isHiddenSubscribers() {
        return hiddenSubscribers;
    }

    public void setHiddenSubscribers(boolean hiddenSubscribers) {
        this.hiddenSubscribers = hiddenSubscribers;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getVideos() {
        return videos;
    }

    public void setVideos(long videos) {
        this.videos = videos;
    }

    public Date getUpdate() {
        return update;
    }

    public void setUpdate(Date update) {
        this.update = update;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}

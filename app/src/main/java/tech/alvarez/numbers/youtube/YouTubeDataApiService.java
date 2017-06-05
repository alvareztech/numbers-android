package tech.alvarez.numbers.youtube;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import tech.alvarez.numbers.model.VideoResponse;
import tech.alvarez.numbers.model.youtube.ChannelsResponse;
import tech.alvarez.numbers.model.youtube.search.SearchResponse;

/**
 * Created by Daniel Alvarez on 7/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public interface YouTubeDataApiService {

    @GET("videos")
    Call<VideoResponse> getVideos(@Query("id") String ide, @Query("key") String key, @Query("part") String part);

    @GET("movie/{id}")
    Call<VideoResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("search?maxResults=50")
    Call<SearchResponse> search(@Query("q") String query, @Query("key") String apiKey, @Query("part") String part, @Query("type") String type);

    @GET("search?maxResults=50")
    Observable<SearchResponse> search2(@Query("q") String query, @Query("key") String apiKey, @Query("part") String part, @Query("type") String type);

    @GET("channels")
    Call<VideoResponse> getChannels(@Query("key") String apiKey, @Query("part") String part, @Query("id") String ide, @Query("fields") String fields);

    @GET("channels?part=snippet,statistics,brandingSettings")
    Call<ChannelsResponse> getChannels(@Query("key") String apiKey, @Query("id") String ide);

    @GET("channels?part=snippet,statistics,brandingSettings")
    Call<ChannelsResponse> getChannelsWithDetails(@Query("key") String apiKey, @Query("id") String ide);
}

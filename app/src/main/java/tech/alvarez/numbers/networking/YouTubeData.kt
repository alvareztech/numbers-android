package tech.alvarez.numbers.networking

import Search
import VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface YouTubeData {

    @GET("videos")
    fun getVideos(
        @Query("id") ide: String?,
        @Query("key") key: String?,
        @Query("part") part: String?
    ): Call<VideoResponse?>?

    @GET("movie/{id}")
    fun getMovieDetails(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String?
    ): Call<VideoResponse?>?

    @GET("search?maxResults=50")
    fun search(
        @Query("q") query: String?,
        @Query("key") apiKey: String?,
        @Query("part") part: String?,
        @Query("type") type: String?
    ): Call<Search?>?

//    @GET("search?maxResults=50")
//    fun search2(
//        @Query("q") query: String?,
//        @Query("key") apiKey: String?,
//        @Query("part") part: String?,
//        @Query("type") type: String?
//    ): Observable<SearchResponse?>?
//
//    @GET("channels")
//    fun getChannels(
//        @Query("key") apiKey: String?,
//        @Query("part") part: String?,
//        @Query("id") ide: String?,
//        @Query("fields") fields: String?
//    ): Call<VideoResponse?>?
//
//    @GET("channels?part=snippet,statistics,brandingSettings")
//    fun getChannels(
//        @Query("key") apiKey: String?,
//        @Query("id") ide: String?
//    ): Call<ChannelsResponse?>?
//
//    @GET("channels?part=snippet,statistics,brandingSettings")
//    fun getChannels2(
//        @Query("key") apiKey: String?,
//        @Query("id") ide: String?
//    ): Observable<ChannelsResponse?>?
//
//    @GET("channels?part=snippet,statistics,brandingSettings")
//    fun getChannelsWithDetails(
//        @Query("key") apiKey: String?,
//        @Query("id") ide: String?
//    ): Call<ChannelsResponse?>?
//
//    @GET("channels?part=snippet,statistics,brandingSettings")
//    fun getChannelsWithDetails2(
//        @Query("key") apiKey: String?,
//        @Query("id") ide: String?
//    ): Observable<ChannelsResponse?>?
}
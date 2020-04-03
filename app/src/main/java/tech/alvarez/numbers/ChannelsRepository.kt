package tech.alvarez.numbers

import tech.alvarez.numbers.model.Search
import tech.alvarez.numbers.networking.RetrofitService
import tech.alvarez.numbers.networking.YouTubeData

class ChannelsRepository() {

    val apiService = RetrofitService.createService(YouTubeData::class.java)

    suspend fun search(): Search {
        val value = apiService.search(
            "alvarez tech",
            BuildConfig.YOUTUBE_DATA_API_KEY,
            "snippet",
            "channel"
        )
        return value
    }
}
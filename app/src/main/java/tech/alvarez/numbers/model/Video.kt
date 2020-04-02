package tech.alvarez.numbers.model

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    val kind: String? = null,

    @SerializedName("etag")
    val etag: String? = null,

    @SerializedName("items")
    val items: List<Item>? = null
)

data class Item2(
    @SerializedName("snippet")
    val channel: Channel2,
    val statistics: Statistics? = null
)

data class Channel2(
    val channelId: String? = null,
    val channelTitle: String? = null,
    val description: String? = null,
    val thumbnails: Thumbnails? = null
)

data class Statistics(
    val viewCount: String? = null,
    val subscriberCount: String? = null,
    val videoCount: String? = null
)
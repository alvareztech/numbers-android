import com.google.gson.annotations.SerializedName

data class VideoResponse(
    val kind: String? = null,

    @SerializedName("etag")
    val etag: String? = null,

    @SerializedName("items")
    val items: List<Item>? = null
)

data class Item(
    @SerializedName("snippet")
    val channel: Channel? = null,

    @SerializedName("statistics")
    val statistics: Statistics? = null
)

data class Channel(
    @SerializedName("channelId")
    val channelId: String? = null,

    @SerializedName("channelTitle")
    val channelTitle: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("thumbnails")
    val thumbnails: Thumbnails? = null
)

data class Statistics(
    @SerializedName("viewCount")
    val viewCount: String? = null,

    @SerializedName("subscriberCount")
    val subscriberCount: String? = null,

    @SerializedName("videoCount")
    val videoCount: String? = null
)

data class Thumbnails(
    @SerializedName("default")
    val defaultThumbnail: DefaultThumbnail? = null
)

data class DefaultThumbnail(
    @SerializedName("url")
    val url: String? = null
)
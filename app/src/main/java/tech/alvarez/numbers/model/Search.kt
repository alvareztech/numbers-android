package tech.alvarez.numbers.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Search(
    @SerializedName("items")
    var items: List<ItemSearchResponse>
)

data class ItemSearchResponse(
    @SerializedName("snippet")
    var snippet: SnippetSearchResponse? = null
)

data class SnippetSearchResponse(
    @SerializedName("title")
    var title: String? = null,

    @SerializedName("channelId")
    val channelId: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("thumbnails")
    val thumbnails: ThumbnailsSearchResponse? = null
)

data class ThumbnailsSearchResponse(
    @SerializedName("default")
    var defaultThumbnail: DefaultThumbnailSearchResponse? = null
)

data class DefaultThumbnailSearchResponse(
    @SerializedName("url")
    var url: String? = null
)
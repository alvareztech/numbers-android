import com.google.gson.annotations.SerializedName
import java.util.*

data class Search(
    @SerializedName("items")
    private var items: ArrayList<ItemSearchResponse?>? = null
)

data class ItemSearchResponse(
    @SerializedName("snippet")
    private var snippet: SnippetSearchResponse? = null
)

data class SnippetSearchResponse(
    @SerializedName("title")
    private var title: String? = null,

    @SerializedName("channelId")
    private val channelId: String? = null,

    @SerializedName("description")
    private val description: String? = null,

    @SerializedName("thumbnails")
    private val thumbnails: ThumbnailsSearchResponse? = null
)

data class ThumbnailsSearchResponse(
    @SerializedName("default")
    private var defaultThumbnail: DefaultThumbnailSearchResponse? = null
)

data class DefaultThumbnailSearchResponse(
    @SerializedName("url")
    private var url: String? = null
)
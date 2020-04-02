package tech.alvarez.numbers.model

data class Search(var pageInfo: PageInfo, var items: List<Item>)

data class PageInfo(var totalResults: Int, var resultsPerPage: Int)

data class Item(var snippet: Snippet?)

data class Snippet(
    var title: String?,
    val channelId: String?,
    val description: String?,
    val thumbnails: Thumbnails?
)

data class Thumbnails(var default: Thumbnail?, var medium: Thumbnail?, var high: Thumbnail?)

data class Thumbnail(var url: String?, var width: Int?, var height: Int?)
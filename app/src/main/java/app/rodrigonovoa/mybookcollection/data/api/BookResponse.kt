package app.rodrigonovoa.mybookcollection.data.api

data class QueryResult(
    val kind: String,
    val totalItems: Int,
    val items: List<BookResponse>
)

data class BookResponse(
    val id: String,
    val selfLink: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val description: String,
    val publishedDate: String,
    val categories: List<String>?,
    val imageLinks: ImageLinks
)

data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String
)
package app.rodrigonovoa.mybookcollection.data.api

data class BookResponse(
    val id: String,
    val selfLink: String,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val publisher: String,
    val publishedDate: String
)
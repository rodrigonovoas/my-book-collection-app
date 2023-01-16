package app.rodrigonovoa.mybookcollection.data.api

data class QueryResult(
    val kind: String,
    val totalItems: Int,
    val items: List<BookResponse>
)
package app.rodrigonovoa.mybookcollection.data.model

data class Record(val id: Int, val dateTime: Long, val bookId: Int,  val bookName: String,
                  val bookAuthor: String, val bookImageUrl: String,
                  val spentTime: Long)

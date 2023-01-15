package app.rodrigonovoa.mybookcollection.data

data class Record(val id: Int, val dateTime: Long,  val bookName: String,
                  val bookAuthor: String, val bookImageUrl: String,
                  val spentTime: Long)
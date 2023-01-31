package app.rodrigonovoa.mybookcollection.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Book")
data class BookEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var googleBookId: String,
    var dateTime: Long,
    var name: String,
    var author: String,
    var description: String,
    var imageUrl: String
)
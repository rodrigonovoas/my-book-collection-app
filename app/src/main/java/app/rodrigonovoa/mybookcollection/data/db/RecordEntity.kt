package app.rodrigonovoa.mybookcollection.data.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Record",
    foreignKeys = arrayOf(
    ForeignKey(entity = BookEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("bookId"),
        onDelete = ForeignKey.CASCADE)
))

data class RecordEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var dateTime: Long,
    var spentTime: Long,
    var comment: String,
    var bookId: Int
)

package app.rodrigonovoa.mybookcollection.data.db

import androidx.room.ColumnInfo

data class RecordAndBookEntity(
    @ColumnInfo(name="id") val id: Long,
    @ColumnInfo(name="dateTime") val dateTime: Long?,
    @ColumnInfo(name="spentTime") val spentTime: Long?,
    @ColumnInfo(name="name") val name: String?,
    @ColumnInfo(name="author") val author: String?,
    @ColumnInfo(name="imageUrl") val imageUrl: String?,
)
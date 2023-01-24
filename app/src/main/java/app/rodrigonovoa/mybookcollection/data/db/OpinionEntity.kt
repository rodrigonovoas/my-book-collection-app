package app.rodrigonovoa.mybookcollection.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Opinion")
data class OpinionEntity(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    var dateTime: Long,
    var opinion: String
)

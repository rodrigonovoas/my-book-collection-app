package app.rodrigonovoa.mybookcollection.db

import androidx.room.*
import app.rodrigonovoa.mybookcollection.data.db.RecordEntity

@Dao
interface RecordDAO {
    @Insert
    fun insertRecord(record: RecordEntity): Long

    @Query("select * from record")
    fun gelAllRecord(): List<RecordEntity>

    @Update
    fun updateRecord(record: RecordEntity)

    @Delete
    fun deleteRecord(record: RecordEntity)
}
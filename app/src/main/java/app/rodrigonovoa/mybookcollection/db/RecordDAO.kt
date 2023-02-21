package app.rodrigonovoa.mybookcollection.db

import androidx.room.*
import app.rodrigonovoa.mybookcollection.data.db.RecordAndBookEntity
import app.rodrigonovoa.mybookcollection.data.db.RecordEntity

@Dao
interface RecordDAO {
    @Insert
    fun insertRecord(record: RecordEntity): Long

    @Query("select record.id, record.dateTime, record.spentTime, book.name, book.author, book.imageUrl from record left join book on book.id = record.bookId")
    fun gelAllRecord(): List<RecordAndBookEntity>

    @Query("select record.id, book.name as client_name, book.imageUrl as city_name from record left join book on book.id = record.bookId where record.id = :id")
    fun getRecordById(id: Int): RecordAndBookEntity

    @Query("select record.id, record.dateTime, record.spentTime, book.name, book.author, book.imageUrl from record left join book on book.id = record.bookId where record.bookId = :bookId")
    fun getRecordsByBookId(bookId: Int): List<RecordAndBookEntity>

    @Query("select * from record where dateTime = :day and bookId = :bookId")
    fun getRecordsByBookIdAndDay(bookId:Int, day: Long): List<RecordAndBookEntity>

    @Query("select * from record where bookId = :bookId and dateTime between :firstDay and :lastDay")
    fun getRecordsByBookIdAndInterval(bookId:Int, firstDay: Long, lastDay: Long): List<RecordAndBookEntity>

    @Update
    fun updateRecord(record: RecordEntity)

    @Delete
    fun deleteRecord(record: RecordEntity)
}
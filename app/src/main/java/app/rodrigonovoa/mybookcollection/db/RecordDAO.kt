package app.rodrigonovoa.mybookcollection.db

import androidx.room.*
import app.rodrigonovoa.mybookcollection.data.db.RecordAndBookEntity
import app.rodrigonovoa.mybookcollection.data.db.RecordEntity

@Dao
interface RecordDAO {
    @Insert
    fun insertRecord(record: RecordEntity): Long

    @Query("select record.id, book.name as book_name, book.imageUrl as book_image_url from record left join book on book.id = record.bookId where record.id = :id")
    fun getRecordById(id: Int): RecordAndBookEntity

    @Query("select record.id, record.dateTime, record.spentTime, record.comment AS comment, book.id AS bookId, book.name, book.author, book.imageUrl from record left join book on book.id = record.bookId where record.bookId = :bookId")
    fun getRecordsByBookId(bookId: Int): List<RecordAndBookEntity>

    @Query("select * from record where bookId = :bookId and dateTime between :firstDay and :lastDay")
    fun getRecordsByBookIdAndInterval(bookId:Int, firstDay: Long, lastDay: Long): List<RecordEntity>

    @Query("select record.id, record.dateTime, record.spentTime, record.comment AS comment, book.id AS bookId, book.name, book.author, book.imageUrl from record left join book on book.id = record.bookId where record.dateTime between :firstDay and :lastDay")
    fun getRecordsFromInterval(firstDay: Long, lastDay: Long): List<RecordAndBookEntity>

    @Update
    fun updateRecord(record: RecordEntity)

    @Delete
    fun deleteRecord(record: RecordEntity)

    @Query("DELETE FROM RECORD WHERE ID = :id")
    fun deleteRecordById(id: Long): Int
}
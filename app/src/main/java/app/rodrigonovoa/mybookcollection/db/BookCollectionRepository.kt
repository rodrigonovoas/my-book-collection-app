package app.rodrigonovoa.mybookcollection.db

import android.content.Context
import app.rodrigonovoa.mybookcollection.data.db.BookEntity
import app.rodrigonovoa.mybookcollection.data.db.RecordAndBookEntity
import app.rodrigonovoa.mybookcollection.data.db.RecordEntity
import kotlinx.coroutines.CoroutineScope

class BookCollectionRepository(context: Context, scope: CoroutineScope) {
    var bookDao: BookDAO = BookCollectionDatabase.Companion.getDatabase(context, scope).bookDao()
    var recordDAO: RecordDAO = BookCollectionDatabase.Companion.getDatabase(context, scope).recordDao()
    /**
     * BOOK
     */

    suspend fun getAllBooks(): List<BookEntity> {
        return bookDao.getAllBooks()
    }

    suspend fun getBookById(bookId: Int): BookEntity {
        return bookDao.getBookById(bookId)
    }

    fun insertBook(book: BookEntity): Long {
        return bookDao.insertBook(book)
    }

    fun updateBook(book: BookEntity) {
        bookDao.updateBook(book)
    }

    fun deleteBook(book: BookEntity) {
        bookDao.deleteBook(book)
    }

    fun deleteBookById(id: Long): Int {
        return bookDao.deleteBookById(id)
    }

    /**
     * RECORD
     */

    fun getRecordById(id: Int): RecordAndBookEntity {
        return recordDAO.getRecordById(id)
    }

    fun getRecordsByBookId(id: Int): List<RecordAndBookEntity> {
        return recordDAO.getRecordsByBookId(id)
    }

    fun getRecordsByBookIdAndInterval(bookId: Int, firstDay: Long, lastDay: Long): List<RecordEntity> {
        return recordDAO.getRecordsByBookIdAndInterval(bookId, firstDay, lastDay)
    }

    fun getRecordsFromInterval(firstDay: Long, lastDay: Long): List<RecordAndBookEntity> {
        return recordDAO.getRecordsFromInterval(firstDay, lastDay)
    }

    fun insertRecord(record: RecordEntity): Long {
        return recordDAO.insertRecord(record)
    }

    fun updateRecord(record: RecordEntity) {
        recordDAO.updateRecord(record)
    }

    fun deleteRecord(record: RecordEntity) {
        recordDAO.deleteRecord(record)
    }

    fun deleteRecordById(id: Long): Int {
        return recordDAO.deleteRecordById(id)
    }
}
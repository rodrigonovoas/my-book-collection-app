package app.rodrigonovoa.mybookcollection.db

import android.content.Context
import app.rodrigonovoa.mybookcollection.data.db.BookEntity
import app.rodrigonovoa.mybookcollection.data.db.OpinionEntity
import app.rodrigonovoa.mybookcollection.data.db.RecordAndBookEntity
import app.rodrigonovoa.mybookcollection.data.db.RecordEntity
import kotlinx.coroutines.CoroutineScope

class BookCollectionRepository(context: Context, scope: CoroutineScope) {
    var bookDao: BookDAO = BookCollectionDatabase.Companion.getDatabase(context, scope).bookDao()
    var recordDAO: RecordDAO = BookCollectionDatabase.Companion.getDatabase(context, scope).recordDao()
    var opinionDAO: OpinionDAO = BookCollectionDatabase.Companion.getDatabase(context, scope).opinionDao()

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

    /**
     * RECORD
     */

    fun getAllRecords(): List<RecordAndBookEntity> {
        return recordDAO.gelAllRecord()
    }

    fun getRecordById(id: Int): RecordAndBookEntity {
        return recordDAO.getRecordById(id)
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

    /**
     * OPINION
     */

    fun getAllOpinion(): List<OpinionEntity> {
        return opinionDAO.getAllOpinion()
    }

    fun insertOpinion(opinion: OpinionEntity): Long {
        return opinionDAO.insertOpinion(opinion)
    }

    fun updateOpinion(opinion: OpinionEntity) {
        opinionDAO.insertOpinion(opinion)
    }

    fun deleteOpinion(opinion: OpinionEntity) {
        opinionDAO.deleteOpinion(opinion)
    }
}
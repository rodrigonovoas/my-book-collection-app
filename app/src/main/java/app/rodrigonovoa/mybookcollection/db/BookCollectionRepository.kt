package app.rodrigonovoa.mybookcollection.db

import android.content.Context
import app.rodrigonovoa.mybookcollection.data.db.BookEntity
import kotlinx.coroutines.CoroutineScope

class BookCollectionRepository(context: Context, scope: CoroutineScope) {
    var bookDao: BookDAO = BookCollectionDatabase.Companion.getDatabase(context, scope).bookDao()
    var db: BookDAO = BookCollectionDatabase.Companion.getDatabase(context, scope).bookDao()


    //Fetch All the Users
    fun getAllUsers(): List<BookEntity> {
        return db.gelAllBooks()
    }

    // Insert new user
    fun insertBook(book: BookEntity): Long {
        return db.insertBook(book)
    }

    // update user
    fun updateBook(book: BookEntity) {
        db.updateBook(book)
    }

    // Delete user
    fun deleteBook(book: BookEntity) {
        db.deleteBook(book)
    }
}
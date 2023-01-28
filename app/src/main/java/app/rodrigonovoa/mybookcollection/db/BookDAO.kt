package app.rodrigonovoa.mybookcollection.db

import androidx.room.*
import app.rodrigonovoa.mybookcollection.data.db.BookEntity

@Dao
interface BookDAO {
    @Insert
    fun insertBook(book: BookEntity): Long

    @Query("select * from book")
    fun getAllBooks(): List<BookEntity>

    @Update
    fun updateBook(user: BookEntity)

    @Delete
    fun deleteBook(user: BookEntity)
}
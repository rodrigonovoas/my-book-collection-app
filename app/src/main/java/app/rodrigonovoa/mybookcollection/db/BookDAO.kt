package app.rodrigonovoa.mybookcollection.db

import androidx.room.*
import app.rodrigonovoa.mybookcollection.data.db.BookEntity

@Dao
interface BookDAO {
    @Insert
    fun insertBook(book: BookEntity): Long

    @Query("select * from book")
    fun getAllBooks(): List<BookEntity>

    @Query("select * from book where id=:id")
    fun getBookById(id: Int): BookEntity

    @Update
    fun updateBook(user: BookEntity)

    @Delete
    fun deleteBook(user: BookEntity)
}
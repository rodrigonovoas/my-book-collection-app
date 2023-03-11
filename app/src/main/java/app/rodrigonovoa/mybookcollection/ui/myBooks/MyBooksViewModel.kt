package app.rodrigonovoa.mybookcollection.ui.myBooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.mybookcollection.data.db.BookEntity
import app.rodrigonovoa.mybookcollection.data.model.Book
import app.rodrigonovoa.mybookcollection.db.BookCollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class BookRemovedState {
    NONE,
    REMOVED,
    NOT_REMOVED
}
class MyBooksViewModel(val localDbRepository: BookCollectionRepository) : ViewModel() {
    private val _localDbBooks = MutableLiveData<List<Book>>().apply { postValue(listOf()) }
    val localDbBooks: LiveData<List<Book>> get() = _localDbBooks

    private val _afterBookRemoved = MutableLiveData<BookRemovedState>().apply { postValue(BookRemovedState.NONE) }
    val afterBookRemoved: LiveData<BookRemovedState> get() = _afterBookRemoved

    fun getBookList() {
        viewModelScope.launch(Dispatchers.IO) {
            val books = localDbRepository.getAllBooks()
            _localDbBooks.postValue(mapBooks(books))
        }
    }

    private fun mapBooks(bookList: List<BookEntity>): List<Book> {
        var books = mutableListOf<Book>()
        for (book in bookList) {
            books.add(Book(book.id ?: 0, book.name, book.author, book.description ?: "", book.imageUrl))
        }
        return books
    }

    fun removeBookFromLocalDb(bookId: Long) {
        checkBookRecords(bookId)
    }

    private fun checkBookRecords(bookId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val records = localDbRepository.getRecordsByBookId(bookId.toInt())

            if (records.isEmpty()) {
                val deleted = localDbRepository.deleteBookById(bookId)
                if (deleted > 0) _afterBookRemoved.postValue(BookRemovedState.REMOVED)
            } else {
                _afterBookRemoved.postValue(BookRemovedState.NOT_REMOVED)
            }
        }
    }


}
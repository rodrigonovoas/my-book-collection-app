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

class MyBooksViewModel(val localDbRepository: BookCollectionRepository) : ViewModel() {
    private val _localDbBooks = MutableLiveData<List<Book>>().apply { postValue(listOf()) }
    val localDbBooks: LiveData<List<Book>> get() = _localDbBooks

    fun getBookList() {
        viewModelScope.launch(Dispatchers.IO) {
            val books = localDbRepository.getAllBooks()
            _localDbBooks.postValue(mapBooks(books))
        }
    }

    private fun mapBooks(bookList: List<BookEntity>): List<Book> {
        var books = mutableListOf<Book>()
        for (book in bookList) {
            books.add(Book(book.name, book.author, book.description, book.imageUrl))
        }
        return books
    }
}
package app.rodrigonovoa.mybookcollection.ui.bookBrowser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.mybookcollection.api.GoogleBooksRepository
import app.rodrigonovoa.mybookcollection.data.api.BookResponse
import app.rodrigonovoa.mybookcollection.data.db.BookEntity
import app.rodrigonovoa.mybookcollection.db.BookCollectionRepository
import app.rodrigonovoa.mybookcollection.utils.DateUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class BookBrowserViewModel(private val googleBooksRepository: GoogleBooksRepository,
                           private val bookCollectionRepository: BookCollectionRepository): ViewModel() {

    private val localStoredBooksIds: MutableList<String> = mutableListOf()
    private val _downloadedBooks = MutableLiveData<List<BookResponse>>().apply { postValue(listOf())}
    val downloadedBooks: LiveData<List<BookResponse>> get() = _downloadedBooks

    init {
        getIdsFromLocalDb()
    }

    private fun getIdsFromLocalDb() {
        viewModelScope.launch(Dispatchers.IO) {
            val bookList = bookCollectionRepository.getAllBooks()
            for (book in bookList) {
                localStoredBooksIds.add(book.googleBookId)
            }
        }
    }

    fun getBooksFromApi(bookName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            googleBooksRepository.getBooksFromApiByName(bookName)
                .catch {}
                .collect {
                    val books = it.body()
                    if(books != null){
                        _downloadedBooks.postValue(books.items)
                    } else {
                        _downloadedBooks.postValue(listOf())
                    }
                }
        }
    }

    fun addBookToLocalDb(book: BookResponse) {
        if (isBookAlreadyAdded(book)) { return }

        val newBook = createBookEntity(book)

        viewModelScope.launch(Dispatchers.IO) {
            val inserted = bookCollectionRepository.insertBook(newBook)
            if(inserted > 0) { getIdsFromLocalDb() }
        }
    }

    private fun createBookEntity(book: BookResponse): BookEntity {
        val newBook = BookEntity(
            null,
            book.id,
            DateUtils.getCurrentDateTimeAsTimeStamp(),
            book.volumeInfo.title,
            book.volumeInfo.authors?.get(0) ?: "",
            book.volumeInfo.description,
            book.volumeInfo.imageLinks.smallThumbnail
        )
        return newBook
    }

    private fun isBookAlreadyAdded(book: BookResponse): Boolean {
        val storedBook = localStoredBooksIds.find { googleId -> book.id.equals(googleId) }
        if(storedBook != null) { return true }
        return false
    }

}
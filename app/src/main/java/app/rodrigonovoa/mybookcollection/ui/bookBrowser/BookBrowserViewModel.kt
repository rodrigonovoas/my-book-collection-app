package app.rodrigonovoa.mybookcollection.ui.bookBrowser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rodrigonovoa.mybookcollection.api.GoogleBooksRepository
import app.rodrigonovoa.mybookcollection.data.api.BookResponse
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class BookBrowserViewModel(val repository: GoogleBooksRepository): ViewModel() {
    private val _downloadedBooks = MutableLiveData<List<BookResponse>>().apply { postValue(listOf())}
    val downloadedBooks: LiveData<List<BookResponse>> get() = _downloadedBooks

    fun getBooksFromApi(bookName: String) {
        viewModelScope.launch {
            repository.getBooksFromApiByName(bookName)
                .catch {
                    // Log.d("DEBUG--", it.message.toString()  + " - 111")
                }
                .collect {
                    val books = it.body()
                    if(books != null){
                        _downloadedBooks.postValue(books.items)
                    }
                }
        }
    }
}
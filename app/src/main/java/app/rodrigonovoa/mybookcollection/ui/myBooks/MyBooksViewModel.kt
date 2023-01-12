package app.rodrigonovoa.mybookcollection.ui.myBooks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.rodrigonovoa.mybookcollection.api.FakeGoogleBooksApi
import app.rodrigonovoa.mybookcollection.api.GoogleBooksApiInterface
import app.rodrigonovoa.mybookcollection.data.Book

class MyBooksViewModel : ViewModel() {
    private val googleApi: GoogleBooksApiInterface = FakeGoogleBooksApi()

    fun getBookList(): List<Book> {
        return googleApi.getBooksFromApi()
    }
}
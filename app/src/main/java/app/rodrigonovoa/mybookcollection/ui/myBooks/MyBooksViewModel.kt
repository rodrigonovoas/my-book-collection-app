package app.rodrigonovoa.mybookcollection.ui.myBooks

import androidx.lifecycle.ViewModel
import app.rodrigonovoa.mybookcollection.api.FakeGoogleBooksApi
import app.rodrigonovoa.mybookcollection.data.model.Book

class MyBooksViewModel : ViewModel() {
    private val googleApi = FakeGoogleBooksApi()

    fun getBookList(): List<Book> {
        return googleApi.getBooksFromApi()
    }
}
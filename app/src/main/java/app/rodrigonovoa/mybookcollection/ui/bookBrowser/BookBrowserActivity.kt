package app.rodrigonovoa.mybookcollection.ui.bookBrowser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.mybookcollection.R
import app.rodrigonovoa.mybookcollection.api.GoogleBooksRepository

class BookBrowserActivity : AppCompatActivity() {
    private lateinit var viewModel: BookBrowserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_browser)

        viewModel = BookBrowserViewModel(GoogleBooksRepository())
        viewModel.getBooksFromApi("misery")


        viewModel.downloadedBooks.observe(this) { it ->
            val rc = findViewById<RecyclerView>(R.id.rc_books)
            val adapter = BookBrowserListAdapter(it)
            rc.layoutManager = LinearLayoutManager(this)
            rc.adapter = adapter
        }
    }
}
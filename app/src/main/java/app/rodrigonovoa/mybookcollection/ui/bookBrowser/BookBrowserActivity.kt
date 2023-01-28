package app.rodrigonovoa.mybookcollection.ui.bookBrowser

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.mybookcollection.R
import app.rodrigonovoa.mybookcollection.data.api.BookResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookBrowserActivity : AppCompatActivity() {
    private val viewModel: BookBrowserViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_browser)

        setObservables()
        viewListeneres()
    }

    private fun setObservables() {
        viewModel.downloadedBooks.observe(this) { it ->
            setRecyclerviewAdapter(it)
        }
    }

    private fun setRecyclerviewAdapter(it: List<BookResponse>) {
        val rc = findViewById<RecyclerView>(R.id.rc_books)
        val adapter = BookBrowserListAdapter(it)
        rc.layoutManager = LinearLayoutManager(this)
        rc.adapter = adapter

        adapter.onItemClick = { book ->
            viewModel.addBookToLocalDb(book)
        }
    }

    private fun viewListeneres() {
        searchListener()
    }

    private fun searchListener() {
        val edtSearch = findViewById<EditText>(R.id.edt_search)
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.getBooksFromApi(s.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }
}
package app.rodrigonovoa.mybookcollection.ui.bookBrowser

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.mybookcollection.R
import app.rodrigonovoa.mybookcollection.data.api.BookResponse
import com.bumptech.glide.Glide
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
            openBookDetailDialog(book)
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

    // TODO: separate into another class
    private fun openBookDetailDialog(book: BookResponse) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_api_book_detail)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        );

        val tvClose = dialog.findViewById(R.id.tv_close) as TextView
        val imvCover = dialog.findViewById(R.id.imv_book_cover) as ImageView
        val tvTitle = dialog.findViewById(R.id.tv_book_title) as TextView
        val tvAuthor = dialog.findViewById(R.id.tv_book_author) as TextView
        val tvDescription = dialog.findViewById(R.id.tv_book_description) as TextView
        val tvCategories = dialog.findViewById(R.id.tv_book_categories) as TextView
        val addBtn = dialog.findViewById(R.id.btn_add) as Button

        tvTitle.setText(book.volumeInfo.title)
        tvDescription.setText(book.volumeInfo.description)
        tvAuthor.setText(book.volumeInfo.authors?.get(0) ?: "")
        tvCategories.setText(book.volumeInfo.categories?.get(0) ?: "")

        if (book.volumeInfo.imageLinks != null) {
            Glide.with(imvCover)
                .load(book.volumeInfo.imageLinks.smallThumbnail)
                .placeholder(R.drawable.ic_cover_not_found)
                .into(imvCover)
        }

        tvClose.setOnClickListener {
            dialog.dismiss()
        }

        addBtn.setOnClickListener {
            viewModel.addBookToLocalDb(book)
        }

        dialog.show()
    }
}
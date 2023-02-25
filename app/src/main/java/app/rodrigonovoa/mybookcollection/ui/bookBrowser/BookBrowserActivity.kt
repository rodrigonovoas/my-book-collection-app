package app.rodrigonovoa.mybookcollection.ui.bookBrowser

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import app.rodrigonovoa.mybookcollection.data.api.BookResponse
import app.rodrigonovoa.mybookcollection.databinding.ActivityBookBrowserBinding
import app.rodrigonovoa.mybookcollection.ui.dialogs.ApiBookDetailDialog
import app.rodrigonovoa.mybookcollection.utils.SnackBarUtils
import org.koin.androidx.viewmodel.ext.android.viewModel


class BookBrowserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookBrowserBinding
    private val viewModel: BookBrowserViewModel by viewModel()
    private lateinit var apiBookDetailDialog: ApiBookDetailDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookBrowserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        apiBookDetailDialog = ApiBookDetailDialog(this)

        apiBookDetailDialog.onItemClick  = { book ->
            viewModel.addBookToLocalDb(book)
        }

        setObservables()
        viewListeneres()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun setObservables() {
        viewModel.downloadedBooks.observe(this) { it ->
            setRecyclerviewAdapter(it)
        }

        viewModel.bookAdded.observe(this) { it ->
            if (it == BookAddedStatus.ADDED) {
                apiBookDetailDialog.close()
                SnackBarUtils.showPositiveMessage(binding.root,"Boook added")
            } else if (it == BookAddedStatus.ALREADY_ADDED) {
                apiBookDetailDialog.close()
                SnackBarUtils.showNegativeMessage(binding.root,"Boook already added")
            }
        }
    }

    private fun setRecyclerviewAdapter(it: List<BookResponse>) {
        val adapter = BookBrowserListAdapter(it)
        binding.rcBooks.layoutManager = LinearLayoutManager(this)
        binding.rcBooks.adapter = adapter

        adapter.onItemClick = { book ->
            apiBookDetailDialog.openBookDetailDialog(book)
        }
    }

    private fun viewListeneres() {
        searchListener()
    }

    private fun searchListener() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
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
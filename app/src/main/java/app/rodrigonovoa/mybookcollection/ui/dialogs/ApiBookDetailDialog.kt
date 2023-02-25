package app.rodrigonovoa.mybookcollection.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import app.rodrigonovoa.mybookcollection.R
import app.rodrigonovoa.mybookcollection.data.api.BookResponse
import com.bumptech.glide.Glide

class ApiBookDetailDialog(val context: Context) {
    private lateinit var dialog: Dialog
    var onItemClick: ((BookResponse) -> Unit)? = null

    init {
        dialogSetup()
    }

    fun openBookDetailDialog(book: BookResponse) {
        val imvClose = dialog.findViewById(R.id.imv_close) as ImageView
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

        imvClose.setOnClickListener {
            dialog.dismiss()
        }

        addBtn.setOnClickListener {
            onItemClick?.invoke(book)
        }

        dialog.show()
    }

    fun close() {
        dialog.dismiss()
    }

    private fun dialogSetup() {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_api_book_detail)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.CENTER);
    }
}
package app.rodrigonovoa.mybookcollection.ui.bookBrowser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.mybookcollection.R
import app.rodrigonovoa.mybookcollection.data.api.BookResponse
import com.bumptech.glide.Glide

class BookBrowserListAdapter(private val booksList: List<BookResponse>) : RecyclerView.Adapter<BookBrowserListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_browser_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookItem = booksList[position]

        loadCover(holder, bookItem)
        holder.tvTitle.text = bookItem.volumeInfo.title
    }

    private fun loadCover(
        holder: BookBrowserListAdapter.ViewHolder,
        bookItem: BookResponse
    ) {
        Glide.with(holder.imvCover.context)
            .load(bookItem.volumeInfo.imageLinks.smallThumbnail)
            .into(holder.imvCover);
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_book_title)
        val imvCover: ImageView = itemView.findViewById(R.id.imv_book_cover)
    }
}

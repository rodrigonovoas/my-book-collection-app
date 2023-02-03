package app.rodrigonovoa.mybookcollection.ui.myBooks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.mybookcollection.R
import app.rodrigonovoa.mybookcollection.data.model.Book
import com.bumptech.glide.Glide

class MyBooksListAdapter(private val booksList: List<Book>) : RecyclerView.Adapter<MyBooksListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookItem = booksList[position]

        loadCover(holder, bookItem)
        setTexts(holder, bookItem)
    }

    private fun setTexts(
        holder: ViewHolder,
        bookItem: Book
    ) {
        holder.tvTitle.text = bookItem.name
        holder.tvAuthor.text = bookItem.author
    }

    private fun loadCover(
        holder: ViewHolder,
        bookItem: Book
    ) {
        Glide.with(holder.imvCover.context)
            .load(bookItem.imageUrl)
            .placeholder(R.drawable.ic_cover_not_found)
            .into(holder.imvCover)
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imvCover: ImageView = itemView.findViewById(R.id.imv_book_cover)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_book_name)
        val tvAuthor: TextView = itemView.findViewById(R.id.tv_book_author)
    }
}

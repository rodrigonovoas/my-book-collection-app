package app.rodrigonovoa.mybookcollection.ui.myBooks

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.mybookcollection.R
import app.rodrigonovoa.mybookcollection.data.Book

class MyBooksListAdapter(private val booksList: List<Book>) : RecyclerView.Adapter<MyBooksListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bookItem = booksList[position]
        // holder.imvCover.setImageResource(ItemsViewModel.image)
        holder.tvTitle.text = bookItem.name
    }

    override fun getItemCount(): Int {
        return booksList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imvCover: ImageView = itemView.findViewById(R.id.imv_book_cover)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_book_name)
    }
}

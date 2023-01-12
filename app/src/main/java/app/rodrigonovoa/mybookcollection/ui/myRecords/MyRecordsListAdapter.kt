package app.rodrigonovoa.mybookcollection.ui.myRecords

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.mybookcollection.R
import app.rodrigonovoa.mybookcollection.data.Record

class MyRecordsListAdapter(private val recordsList: List<Record>) : RecyclerView.Adapter<MyRecordsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.record_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recordItem = recordsList[position]

        holder.tvBookTitle.text = recordItem.bookName
    }

    override fun getItemCount(): Int {
        return recordsList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvBookTitle: TextView = itemView.findViewById(R.id.tv_book_name)
    }
}
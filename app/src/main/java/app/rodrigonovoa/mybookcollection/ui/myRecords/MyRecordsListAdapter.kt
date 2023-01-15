package app.rodrigonovoa.mybookcollection.ui.myRecords

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.rodrigonovoa.mybookcollection.R
import app.rodrigonovoa.mybookcollection.data.Record
import app.rodrigonovoa.mybookcollection.utils.DateUtils
import com.bumptech.glide.Glide

class MyRecordsListAdapter(private val recordsList: List<Record>) : RecyclerView.Adapter<MyRecordsListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.record_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recordItem = recordsList[position]

        holder.tvBookTitleAndAuthor.text = recordItem.bookName + " (${recordItem.bookAuthor})"
        holder.tvBookSpentTime.text = DateUtils.fromMillisecondsToDateString(recordItem.spentTime)
        loadCover(holder, recordItem)
    }

    private fun loadCover(
        holder: MyRecordsListAdapter.ViewHolder,
        recordItem: Record
    ) {
        Glide.with(holder.imvBookCover.context)
            .load(recordItem.bookImageUrl)
            .into(holder.imvBookCover);
    }

    override fun getItemCount(): Int {
        return recordsList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvBookTitleAndAuthor: TextView = itemView.findViewById(R.id.tv_book_name_and_author)
        val tvBookSpentTime: TextView = itemView.findViewById(R.id.tv_record_spent_time)
        val imvBookCover: ImageView = itemView.findViewById(R.id.imv_book_cover)
    }
}
package app.rodrigonovoa.mybookcollection.ui.addRecord

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import app.rodrigonovoa.mybookcollection.R
import app.rodrigonovoa.mybookcollection.data.db.BookEntity
import com.bumptech.glide.Glide

class SpinnerCustomAdapter(val context: Context, var dataSource: List<BookEntity>) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val itemHolder: ItemHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.add_record_spinner, parent, false)
            itemHolder = ItemHolder(view)
            view?.tag = itemHolder
        } else {
            view = convertView
            itemHolder = view.tag as ItemHolder
        }

        itemHolder.title.text = dataSource.get(position).name

        Glide.with(context)
            .load(dataSource.get(position).imageUrl)
            .placeholder(R.drawable.ic_cover_not_found)
            .into(itemHolder.cover)

        return view
    }

    override fun getItem(position: Int): Any? {
        return dataSource[position];
    }

    override fun getCount(): Int {
        return dataSource.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val title: TextView
        val cover: ImageView

        init {
            title = row?.findViewById(R.id.bookTitle) as TextView
            cover = row?.findViewById(R.id.bookCover) as ImageView
        }
    }

}
package app.rodrigonovoa.mybookcollection.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import app.rodrigonovoa.mybookcollection.R
import app.rodrigonovoa.mybookcollection.data.model.Record
import app.rodrigonovoa.mybookcollection.utils.DateUtils
import com.bumptech.glide.Glide

class RecordDetailDialog(val context: Context) {
    var onItemClick: ((Record, String) -> Unit)? = null
    private lateinit var dialog: Dialog

    init {
        dialogSetup()
    }

    fun openRecordDetailDialog(record: Record) {
        val imvClose = dialog.findViewById(R.id.imv_close) as ImageView
        val imvCover = dialog.findViewById(R.id.imv_book_cover) as ImageView
        val tvTitle = dialog.findViewById(R.id.tv_book_title) as TextView
        val tvAuthor = dialog.findViewById(R.id.tv_book_author) as TextView
        val imvEditComment = dialog.findViewById(R.id.imv_edit_comment) as ImageView
        val edtComment = dialog.findViewById(R.id.edt_comment) as EditText

        tvTitle.setText(record.bookName)
        tvAuthor.setText(record.bookAuthor)
        edtComment.setText(record.comment)

        if (record.bookImageUrl.isNotEmpty()) {
            Glide.with(imvCover)
                .load(record.bookImageUrl)
                .placeholder(R.drawable.ic_cover_not_found)
                .into(imvCover)
        }

        imvClose.setOnClickListener {
            dialog.dismiss()
        }

        imvEditComment.setOnClickListener {
            if(edtComment.isEnabled) {
                imvEditComment.setImageDrawable(context.getDrawable(R.drawable.ic_edit_comment))
                onItemClick?.invoke(record, edtComment.text.toString())
                edtComment.isEnabled = false
            } else {
                imvEditComment.setImageDrawable(context.getDrawable(R.drawable.ic_save_comment))
                edtComment.isEnabled = true
            }
        }

        dialog.show()
    }

    private fun dialogSetup() {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_record_detail)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.CENTER);
    }

    fun setTotalHours(totalHours: Long) {
        val tvTotalHours = dialog.findViewById(R.id.tv_total_hours) as TextView
        var totalHoursString = "0"
        if (totalHours > 0) totalHoursString = DateUtils.fromMillisecondsToDateString(totalHours)
        if (dialog != null) tvTotalHours.setText("Total hours: " + totalHoursString)
    }

    fun setTotalRecords(totalRecords: Int) {
        val tvTotalRecords = dialog.findViewById(R.id.tv_total_records) as TextView
        if (dialog != null) tvTotalRecords.setText("Total records: " + totalRecords.toString())
    }
}
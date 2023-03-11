package app.rodrigonovoa.mybookcollection.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import app.rodrigonovoa.mybookcollection.R

class RemoveDialog(val context: Context) {
    private lateinit var dialog: Dialog
    var onItemClick: ((Long) -> Unit)? = null

    init {
        dialogSetup()
    }

    fun openRemoveDialog(id: Long) {
        val tvTitle = dialog.findViewById(R.id.tv_title) as TextView
        val tvAuthor = dialog.findViewById(R.id.tv_subtitle) as TextView
        val btnCancel = dialog.findViewById(R.id.btn_cancel) as Button
        val btnConfirm = dialog.findViewById(R.id.btn_confirm) as Button

        tvTitle.setText("WARNING")
        tvAuthor.setText("This record will be deleted. Do you want to cotinue?")

        btnCancel.setOnClickListener {
            close()
        }

        btnConfirm.setOnClickListener {
            onItemClick?.invoke(id)
            close()
        }

        dialog.show()
    }

    private fun close() {
        dialog.dismiss()
    }

    private fun dialogSetup() {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialog_remove)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setGravity(Gravity.CENTER);
    }
}
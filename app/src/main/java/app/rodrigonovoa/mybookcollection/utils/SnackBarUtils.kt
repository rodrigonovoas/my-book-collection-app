package app.rodrigonovoa.mybookcollection.utils

import android.view.View
import androidx.core.content.ContextCompat
import app.rodrigonovoa.mybookcollection.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

object SnackBarUtils {
    fun showPositiveMessage(view: View, message: String) {
        Snackbar.make(
            view,
            message,
            BaseTransientBottomBar.LENGTH_SHORT
        ).show()
    }

    fun showNegativeMessage(view: View, message: String) {
        val snackbar = Snackbar.make(
            view,
            message,
            BaseTransientBottomBar.LENGTH_SHORT
        )
        snackbar.setBackgroundTint(ContextCompat.getColor(view.context, R.color.tart_orange))
        snackbar.show()
    }
}
package com.rijksmuseum.task.util.presentation

import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.rijksmuseum.task.R
import java.io.IOException

fun Fragment.handleError(error: Throwable) {
    val message = when (error) {
        is IOException -> R.string.internet_error_title
        else -> R.string.unknown_error
    }

    view?.run {
        showMessage(message)
    }
}

private fun View.showMessage(@StringRes message: Int) {
    Snackbar.make(this, context.getString(message), Snackbar.LENGTH_SHORT).show()
}
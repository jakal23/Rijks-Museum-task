package com.rijksmuseum.task.collection.ui.util

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable

fun Context.circularProgressDrawable(): CircularProgressDrawable {
    return CircularProgressDrawable(this).apply {
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }
}
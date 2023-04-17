package com.rijksmuseum.task.collection.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WebImage(
    val guid: String,
    val height: Int,
    val offsetPercentageX: Int,
    val offsetPercentageY: Int,
    val url: String,
    val width: Int
) : Parcelable
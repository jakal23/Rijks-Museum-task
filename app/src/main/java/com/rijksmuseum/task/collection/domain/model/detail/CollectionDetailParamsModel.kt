package com.rijksmuseum.task.collection.domain.model.detail

import android.os.Parcelable
import com.rijksmuseum.task.util.network.AppLanguage
import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionDetailParamsModel(
    val culture: AppLanguage,

    /*
    * The identifier of the object (case-sensitive).
    */
    val objectNumber: String,
) : Parcelable
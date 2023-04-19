package com.rijksmuseum.task.collection.domain.model.detail

import android.os.Parcelable
import com.rijksmuseum.task.util.network.AppLanguage
import kotlinx.parcelize.Parcelize

/**
 * class CollectionDetailParams is request data for retrieving collection details
 *
 * @param[culture] The language to search in (and of the results).
 * @param[objectNumber] The identifier of the object (case-sensitive).
 */
@Parcelize
data class CollectionDetailParamsModel(
    val culture: AppLanguage,
    val objectNumber: String,
) : Parcelable
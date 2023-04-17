package com.rijksmuseum.task.collection.domain.model.list

import android.os.Parcelable
import com.rijksmuseum.task.util.network.AppLanguage
import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionSearchParams(
    /*
    * The language to search in (and of the results).
    */
    val culture: AppLanguage,

    /*
    * The search terms that need to occur in one of the fields of the object data.
    */
    val query: String,

    /*
    * Sort results
    */
    val sort: Sort,

    ) : Parcelable {

    enum class Sort(val value: String) {
        /* Sort results on relevance.") */
        RELEVANCE("relevance"),

        /* Sort results on type.") */
        OBJECTTYPE("objecttype"),

        /* Sort results chronologically (oldest first).") */
        CHRONOLOGIC("chronologic"),

        /* Sort results chronologically (newest first).") */
        ACHRONOLOGIC("achronologic"),

        /* Sort results on artist (a-z).") */
        ARTIST("artist"),

        /* Sort results on artist (z-a).") */
        ARTISTDESC("artistdesc");

        companion object {
            fun findByOrdinal(ordinal: Int): Sort? {
                return Sort.values().find { it.ordinal == ordinal }
            }
        }
    }
}

package com.rijksmuseum.task.collection.presentation.collection.model

import android.net.Uri

sealed class CollectionItem {

    data class Maker(
        val title: String
    ) : CollectionItem()

    data class CollectionDetail(
        val id: String,
        val title: String,
        val description: String,
        val objectNumber: String,
        val maker: String,
        val link: Uri?,
        val imageUrl: Uri?
    ) : CollectionItem()

    /**
     * Check [CollectionItem] is [CollectionDetail] or not
     * @return [Boolean] true is [CollectionDetail], false is otherwise
     */
    fun isCollectionDetail() = this is CollectionDetail

    /**
     * Check [CollectionItem] is [Maker] or not
     * @return [Boolean] true is [Maker], false is otherwise
     */
    fun isMaker() = this is Maker

    fun asCollectionDetail(): CollectionDetail {
        return this as CollectionDetail
    }

    fun asMaker(): Maker {
        return this as Maker
    }

    override fun toString(): String {
        return when (this) {
            is Maker -> "Header[$this]"
            is CollectionDetail -> "Body[$this]"
        }
    }
}

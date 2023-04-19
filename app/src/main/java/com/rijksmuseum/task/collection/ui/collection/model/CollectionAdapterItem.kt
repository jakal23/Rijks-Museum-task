package com.rijksmuseum.task.collection.ui.collection.model

import android.net.Uri

sealed class CollectionAdapterItem {

    data class Header(
        val title: String
    ) : CollectionAdapterItem()

    data class Body(
        val id: String,
        val title: String,
        val description: String,
        val objectNumber: String,
        val maker: String,
        val link: Uri?,
        val imageUrl: Uri?
    ) : CollectionAdapterItem()

    /**
     * Check [CollectionAdapterItem] is Body or not
     * @return [Boolean] true is Body, false is otherwise
     */
    fun isBody() = this is Body

    /**
     * Check [CollectionAdapterItem] is Header or not
     * @return [Boolean] true is Header, false is otherwise
     */
    fun isHeader() = this is Header

    fun asBody(): Body {
        return this as Body
    }

    fun asHeader(): Header {
        return this as Header
    }

    override fun toString(): String {
        return when (this) {
            is Header -> "Header[$this]"
            is Body -> "Body[$this]"
        }
    }
}

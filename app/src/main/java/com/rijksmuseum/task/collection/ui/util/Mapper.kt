package com.rijksmuseum.task.collection.ui.util

import android.net.Uri
import android.util.Log
import com.rijksmuseum.task.collection.domain.model.detail.ArtObjectDetail
import com.rijksmuseum.task.collection.domain.model.list.ArtObject
import com.rijksmuseum.task.collection.ui.collection.model.CollectionAdapterItem
import com.rijksmuseum.task.collection.ui.detail.model.CollectionDetail

fun ArtObject.toAdapterItem(): CollectionAdapterItem.Body {
    return CollectionAdapterItem.Body(
        id = id,
        title = title,
        description = longTitle,
        objectNumber = objectNumber,
        maker = principalOrFirstMaker,
        link = safeParse(links.web),
        imageUrl = imageUri()
    )
}

fun ArtObjectDetail.toCollectionDetail(): CollectionDetail {
    val image = if (hasImage && showImage) webImage.url else null

    return CollectionDetail(
        id, description, title, principalMaker, image
    )
}

private fun ArtObject.imageUri(): Uri? {
    return when {
        hasImage && showImage -> safeParse(headerImage.url)
        else -> null
    }
}

private fun safeParse(uriString: String): Uri? {
    return try {
        Uri.parse(uriString)
    } catch (e: Exception) {
        Log.e("Uri", "Uri parse failed. Uri: $uriString")
        null
    }
}
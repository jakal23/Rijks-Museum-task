package com.rijksmuseum.task.collection.presentation.util

import android.net.Uri
import android.util.Log
import com.rijksmuseum.task.collection.domain.model.detail.ArtObjectDetail
import com.rijksmuseum.task.collection.domain.model.list.ArtObject
import com.rijksmuseum.task.collection.presentation.collection.model.CollectionItem
import com.rijksmuseum.task.collection.presentation.detail.model.CollectionDetailViewData

fun ArtObject.toAdapterItem(): CollectionItem.CollectionDetail {
    return CollectionItem.CollectionDetail(
        id = id,
        title = title,
        description = longTitle,
        objectNumber = objectNumber,
        maker = principalOrFirstMaker,
        link = safeParse(links.web),
        imageUrl = imageUri()
    )
}

fun ArtObjectDetail.toCollectionDetail(): CollectionDetailViewData {
    val image = if (hasImage && showImage) webImage.url else null

    return CollectionDetailViewData(
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
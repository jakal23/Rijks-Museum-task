package com.rijksmuseum.task.collection.domain.model.detail

import android.os.Parcelable
import com.rijksmuseum.task.collection.domain.model.WebImage
import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionDetailResponse(
    val artObject: ArtObjectDetail,
    val elapsedMilliseconds: Int
) : Parcelable

@Parcelize
data class ArtObjectDetail(
    val acquisition: Acquisition,
    val catRefRPK: List<String>,
    val colors: List<Color>,
    val dating: Dating,
    val description: String?,
    val dimensions: List<Dimension>,
    val documentation: List<String>,
    val hasImage: Boolean,
    val id: String,
    val language: String,
    val links: Links,
    val longTitle: String,
    val materials: List<String>,
    val objectCollection: List<String>,
    val objectNumber: String,
    val objectTypes: List<String>,
    val physicalMedium: String,
    val principalMaker: String,
    val principalOrFirstMaker: String,
    val priref: String,
    val productionPlaces: List<String>,
    val scLabelLine: String,
    val showImage: Boolean,
    val subTitle: String,
    val techniques: List<String>,
    val title: String,
    val titles: List<String>,
    val webImage: WebImage
) : Parcelable

@Parcelize
data class Acquisition(
    val date: String,
    val method: String
) : Parcelable

@Parcelize
data class Links(
    val search: String
) : Parcelable

@Parcelize
data class Dating(
    val period: Int,
    val presentingDate: String,
    val sortingDate: Int,
    val yearEarly: Int,
    val yearLate: Int
) : Parcelable

@Parcelize
data class Dimension(
    val part: String,
    val type: String,
    val unit: String,
    val value: String
) : Parcelable

@Parcelize
data class Color(
    val hex: String,
    val percentage: Int
) : Parcelable

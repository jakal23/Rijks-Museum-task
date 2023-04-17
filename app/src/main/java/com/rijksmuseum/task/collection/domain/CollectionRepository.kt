package com.rijksmuseum.task.collection.domain

import androidx.paging.PagingData
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailParams
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailResponse
import com.rijksmuseum.task.collection.domain.model.list.ArtObject
import com.rijksmuseum.task.collection.domain.model.list.CollectionSearchParams
import com.rijksmuseum.task.util.network.Result
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {

    fun loadDetail(params: CollectionDetailParams): Flow<Result<CollectionDetailResponse>>

    fun getPager(pageSize: Int, params: CollectionSearchParams): Flow<PagingData<ArtObject>>
}
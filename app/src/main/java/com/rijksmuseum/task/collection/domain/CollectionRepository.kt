package com.rijksmuseum.task.collection.domain

import androidx.paging.PagingData
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailParamsModel
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailResponse
import com.rijksmuseum.task.collection.domain.model.list.ArtObject
import com.rijksmuseum.task.collection.domain.model.list.CollectionSearchParamsModel
import com.rijksmuseum.task.util.network.Result
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {

    fun loadDetail(params: CollectionDetailParamsModel): Flow<Result<CollectionDetailResponse>>

    fun getPager(pageSize: Int, params: CollectionSearchParamsModel): Flow<PagingData<ArtObject>>
}
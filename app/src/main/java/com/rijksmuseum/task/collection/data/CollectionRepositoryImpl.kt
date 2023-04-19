package com.rijksmuseum.task.collection.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rijksmuseum.task.collection.domain.CollectionRepository
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailParamsModel
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailResponse
import com.rijksmuseum.task.collection.domain.model.list.ArtObject
import com.rijksmuseum.task.collection.domain.model.list.CollectionSearchParamsModel
import com.rijksmuseum.task.util.network.Result
import com.rijksmuseum.task.util.network.flowOfExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class CollectionRepositoryImpl(
    private val service: CollectionService
) : CollectionRepository {

    override fun loadDetail(
        params: CollectionDetailParamsModel
    ): Flow<Result<CollectionDetailResponse>> {
        return flowOfExecutor {
            service.loadCollectionDetails(
                params.culture.code,
                params.objectNumber
            )
        }.flowOn(Dispatchers.IO)
    }

    override fun getPager(
        pageSize: Int,
        params: CollectionSearchParamsModel
    ): Flow<PagingData<ArtObject>> {
        return Pager(
            config = PagingConfig(pageSize)
        ) { getPagingSource(params) }.flow
    }

    private fun getPagingSource(params: CollectionSearchParamsModel): CollectionPagingSource {
        return CollectionPagingSource(service, params)
    }
}
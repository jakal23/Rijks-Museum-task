package com.rijksmuseum.task.collection.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingState
import com.rijksmuseum.task.collection.domain.model.list.ArtObject
import com.rijksmuseum.task.collection.domain.model.list.CollectionSearchParams
import com.rijksmuseum.task.util.BasePagingSource

@OptIn(ExperimentalPagingApi::class)
class CollectionPagingSource(
    private val service: CollectionService,
    private val request: CollectionSearchParams
) : BasePagingSource<ArtObject>() {

    override fun getRefreshKey(state: PagingState<Int, ArtObject>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun page(params: LoadParams<Int>): LoadResult.Page<Int, ArtObject> {
        val pageNumber = params.key ?: 0

        Log.d(TAG, "Load paging data. pageNumber:$pageNumber")

        // Suspending network load via Retrofit. This doesn't need to be wrapped in a
        // withContext(Dispatcher.IO) { ... } block since Retrofit's Coroutine
        // CallAdapter dispatches on a worker thread.
        val response = service.searchCollection(
            request.culture.code,
            request.query,
            request.sort.value,
            pageNumber,
            params.loadSize
        )

        Log.d(TAG, "Loaded paging data. Size:${response.artObjects.size}")

        // Since 0 is the lowest page number, return null to signify no more pages should
        // be loaded before it.
        val prevKey = if (pageNumber > 0) pageNumber - 1 else null

        // This API defines that it's out of data when a page returns empty. When out of
        // data, we return `null` to signify no more pages should be loaded
        val nextKey = if (response.artObjects.isNotEmpty()) pageNumber + 1 else null

        return LoadResult.Page(
            data = response.artObjects,
            prevKey = prevKey,
            nextKey = nextKey
        )
    }

    companion object {
        private val TAG = CollectionPagingSource::class.java.simpleName
    }
}
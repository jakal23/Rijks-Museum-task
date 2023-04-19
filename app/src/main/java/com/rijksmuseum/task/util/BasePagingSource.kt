package com.rijksmuseum.task.util

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
abstract class BasePagingSource<T : Any> : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = page(params)
            Log.i(TAG, "PagingSource success.")
            return page
        } catch (e: IOException) {
            handlerError(e)
        } catch (e: HttpException) {
            handlerError(e)
        }
    }

    private fun handlerError(e: Exception): LoadResult.Error<Int, T> {
        Log.e(TAG, "PagingSource error", e)
        return LoadResult.Error(e)
    }

    protected abstract suspend fun page(
        params: LoadParams<Int>
    ): LoadResult.Page<Int, T>

    companion object {
        private const val TAG = "PagingSource"
    }
}

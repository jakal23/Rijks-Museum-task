package com.rijksmuseum.task.collection.data

import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailResponse
import com.rijksmuseum.task.collection.domain.model.list.CollectionResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface CollectionService {

    @GET("api/{culture}/collection")
    suspend fun searchCollection(
        @Path("culture") languageCode: String,
        @Query("q") query: String,
        @Query("s") sortBy: String,
        @Query("p") page: Int,
        @Query("ps") limit: Int
    ): CollectionResponse

    @GET("api/{culture}/collection/{number}")
    suspend fun loadCollectionDetails(
        @Path("culture") languageCode: String,
        @Path("number") objectNumber: String,
    ): CollectionDetailResponse
}
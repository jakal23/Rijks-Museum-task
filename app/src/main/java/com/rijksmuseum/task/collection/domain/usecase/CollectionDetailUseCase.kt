package com.rijksmuseum.task.collection.domain.usecase

import com.rijksmuseum.task.collection.domain.CollectionRepository
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailParams
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailResponse
import com.rijksmuseum.task.collection.domain.util.DetailValidator
import com.rijksmuseum.task.util.network.Result
import com.rijksmuseum.task.util.validator.InvalidDataException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class CollectionDetailUseCase(
    private val repository: CollectionRepository
) {
    private val validator = DetailValidator()

    operator fun invoke(request: CollectionDetailParams): Flow<Result<CollectionDetailResponse>> {
        val errors = validator.validate(request)

        return if (errors.hasError()) {
            flowOf(Result.Error(InvalidDataException(errors)))
        } else {
            repository.loadDetail(request)
        }
    }
}
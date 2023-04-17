package com.rijksmuseum.task.collection.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailParams
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailResponse
import com.rijksmuseum.task.collection.domain.usecase.CollectionDetailUseCase
import com.rijksmuseum.task.collection.ui.detail.model.CollectionDetail
import com.rijksmuseum.task.collection.ui.util.toCollectionDetail
import com.rijksmuseum.task.util.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(
    private val collectionDetailUseCase: CollectionDetailUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _loader = MutableStateFlow(false)
    val loader = _loader.asStateFlow()

    private val stateFlow = savedStateHandle
        .getLiveData<CollectionDetailParams>(KEY_STATE)
        .asFlow()

    val detail = stateFlow.flatMapMerge {
        collectionDetailUseCase.invoke(it)
            .map { result -> mapResponse(result) }
            .onStart { _loader.emit(true) }
            .onCompletion { _loader.emit(false) }
    }

    fun loadDetail(request: CollectionDetailParams) {
        savedStateHandle[KEY_STATE] = request
    }

    private fun mapResponse(result: Result<CollectionDetailResponse>): Result<CollectionDetail> {
        return when {
            result.isSuccess() -> Result.Success(result.toData().artObject.toCollectionDetail())
            else -> Result.Error(result.toException())
        }
    }

    companion object {
        private const val KEY_STATE = "state"
    }
}

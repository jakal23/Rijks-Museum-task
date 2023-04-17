package com.rijksmuseum.task.collection.ui.collection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.rijksmuseum.task.collection.domain.CollectionRepository
import com.rijksmuseum.task.collection.domain.model.list.ArtObject
import com.rijksmuseum.task.collection.domain.model.list.CollectionSearchParams
import com.rijksmuseum.task.collection.ui.collection.model.CollectionAdapterItem
import com.rijksmuseum.task.collection.ui.util.toAdapterItem
import com.rijksmuseum.task.util.network.AppLanguage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val repository: CollectionRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val state = savedStateHandle.getStateFlow(SEARCH_KEY, DEF_STATE)

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)

    val searchResultFlow = flowOf(
        clearListCh.receiveAsFlow().map { PagingData.empty() },
        state.flatMapLatest { getPager(it) }
            .groupByMaker()
            .cachedIn(viewModelScope)
    ).flattenMerge(2)

    private fun Flow<PagingData<ArtObject>>.groupByMaker(): Flow<PagingData<CollectionAdapterItem>> {
        return this
            .map { pagingData -> pagingData.map { it.toAdapterItem() } }
            .map {
                it.insertSeparators { after, before ->

                    val beforeType = before?.maker ?: return@insertSeparators null
                    val afterType = after?.maker

                    when {
                        beforeType != afterType -> CollectionAdapterItem.Header(beforeType)
                        else -> null // no separator
                    }
                }
            }
    }

    fun state(): CollectionSearchParams {
        return state.value
    }

    private fun getPager(request: CollectionSearchParams): Flow<PagingData<ArtObject>> {
        return repository.getPager(PAGE_SIZE, request)
    }

    private fun shouldSearch(collectionSearchParams: CollectionSearchParams) =
        savedStateHandle.get<CollectionSearchParams>(SEARCH_KEY) != collectionSearchParams

    fun search(query: String) {
        search(
            state.value.copy(query = query)
        )
    }

    fun sort(sort: CollectionSearchParams.Sort) {
        search(
            state.value.copy(sort = sort)
        )
    }

    fun language(language: AppLanguage) {
        search(
            state.value.copy(culture = language)
        )
    }

    private fun search(request: CollectionSearchParams) {
        if (!shouldSearch(request)) return

        clearListCh.trySend(Unit).isSuccess

        savedStateHandle[SEARCH_KEY] = request
    }

    companion object {
        private const val SEARCH_KEY = "search"

        private const val PAGE_SIZE = 30

        private val DEF_STATE = CollectionSearchParams(
            culture = AppLanguage.ENGLISH,
            query = "",
            sort = CollectionSearchParams.Sort.ARTIST
        )
    }
}
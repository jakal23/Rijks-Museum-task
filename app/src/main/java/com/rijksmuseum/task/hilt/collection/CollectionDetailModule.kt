package com.rijksmuseum.task.hilt.collection

import com.rijksmuseum.task.collection.domain.CollectionRepository
import com.rijksmuseum.task.collection.domain.usecase.CollectionDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object CollectionDetailModule {

    @Provides
    @ViewModelScoped
    fun detailUseCase(
        repository: CollectionRepository
    ): CollectionDetailUseCase {
        return CollectionDetailUseCase(repository)
    }

}
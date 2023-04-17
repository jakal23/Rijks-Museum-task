package com.rijksmuseum.task.hilt.collection


import com.rijksmuseum.task.collection.data.CollectionRepositoryImpl
import com.rijksmuseum.task.collection.data.CollectionService
import com.rijksmuseum.task.collection.domain.CollectionRepository
import com.rijksmuseum.task.hilt.network.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object CollectionModule {


    @Provides
    @ViewModelScoped
    fun repository(
        service: CollectionService
    ): CollectionRepository {
        return CollectionRepositoryImpl(service)
    }

    @Provides
    @ViewModelScoped
    fun service(
        @NetworkModule.BaseRetrofit retrofit: Retrofit
    ): CollectionService {
        return retrofit.create(CollectionService::class.java)
    }
}
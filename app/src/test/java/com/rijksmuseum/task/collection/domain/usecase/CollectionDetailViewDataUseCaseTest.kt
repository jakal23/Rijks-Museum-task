package com.rijksmuseum.task.collection.domain.usecase

import com.google.common.truth.Truth
import com.rijksmuseum.task.collection.domain.CollectionRepository
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailParamsModel
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailResponse
import com.rijksmuseum.task.util.network.AppLanguage
import com.rijksmuseum.task.util.network.Result
import com.rijksmuseum.task.util.validator.InvalidDataException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class CollectionDetailViewDataUseCaseTest {

    private val repository = Mockito.mock(CollectionRepository::class.java)

    private lateinit var useCase: CollectionDetailUseCase

    @Before
    fun setUp() {
        useCase = CollectionDetailUseCase(repository)
    }

    @Test
    fun `send invalid data then verify result is InvalidDataException`() = runTest {
        val invalidParam = CollectionDetailParamsModel(AppLanguage.ENGLISH, "")
        Mockito.`when`(repository.loadDetail(invalidParam)).thenAnswer {}

        val res = useCase.invoke(invalidParam).first()

        Truth.assertThat(res).isInstanceOf(Result.Error::class.java)
        Truth.assertThat(res.toException()).isInstanceOf(InvalidDataException::class.java)
    }

    @Test
    fun `send detail params then verify result is success`() = runTest {
        val param = CollectionDetailParamsModel(AppLanguage.ENGLISH, "SC-2")
        Mockito.`when`(repository.loadDetail(param))
            .thenAnswer { (flowOf(Result.Success(Mockito.mock(CollectionDetailResponse::class.java)))) }

        val res = useCase.invoke(param).first()

        Truth.assertThat(res).isInstanceOf(Result.Success::class.java)
    }

    @After
    fun tearDown() {
        Mockito.reset(repository)
    }
}
package com.rijksmuseum.task.util.network

import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.IOException

class FlowOfExecutorKtTest {

    @Test
    fun `verify valid request result is success`() = runTest {
        val res = flowOfExecutor { 1 }
            .first()

        Truth.assertThat(res).isInstanceOf(Result.Success::class.java)
    }

    @Test
    fun `verify invalid request result is error`() = runTest {
        val res = flowOfExecutor {
            throw IOException("No network access")
        }.first()

        Truth.assertThat(res).isInstanceOf(Result.Error::class.java)
    }
}
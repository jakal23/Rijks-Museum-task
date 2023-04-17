package com.rijksmuseum.task.util.validator

import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ErrorsTest{

    private val errorMessage = "No network access"

    @Test
    fun `add errors then verify error size`() = runTest {
        val errors = Errors(1)
        errors.addError("Hello", 1)

        Truth.assertThat(errors.errors().size).isEqualTo(1)
    }

    @Test
    fun `add errors with the same key then verify its updated`() = runTest {
        val errors = Errors(1)
        errors.addError("Hello", 1)
        errors.addError(errorMessage, 1)

        Truth.assertThat(errors.errors().size).isEqualTo(1)
        Truth.assertThat(errors.errors().first().errorMessage).isEqualTo(errorMessage)
    }

    @Test
    fun `add errors then verify added`() = runTest {
        val errors = Errors(1)
        errors.addError(errorMessage, 1)

        Truth.assertThat(errors.errors().first())
            .isEqualTo(Field(errorMessage, 1))
    }

}
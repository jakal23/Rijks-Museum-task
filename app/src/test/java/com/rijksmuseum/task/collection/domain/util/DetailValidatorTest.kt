package com.rijksmuseum.task.collection.domain.util

import com.google.common.truth.Truth
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailParams
import com.rijksmuseum.task.util.network.AppLanguage
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DetailValidatorTest(
    private val data: CollectionDetailParams,
    private val isValid: Boolean
) {

    @Test
    fun `verify is collection detail valid or not`() {
        val validator = DetailValidator()
        val errors = validator.validate(data)

        Truth.assertThat(errors.hasError()).isEqualTo(!isValid)
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: collectionDetail ({0}). is valid ({1})")
        fun data(): List<Array<Any?>> {
            return arrayListOf(
                arrayOf(CollectionDetailParams(AppLanguage.ENGLISH,""), false),
                arrayOf(CollectionDetailParams(AppLanguage.ENGLISH,"a"), true),
                arrayOf(CollectionDetailParams(AppLanguage.ENGLISH,"test"), true),
                arrayOf(CollectionDetailParams(AppLanguage.ENGLISH,"1234"), true),
                arrayOf(CollectionDetailParams(AppLanguage.NETHERLANDS,""), false),
                arrayOf(CollectionDetailParams(AppLanguage.NETHERLANDS,"a"), true),
                arrayOf(CollectionDetailParams(AppLanguage.NETHERLANDS,"test"), true),
                arrayOf(CollectionDetailParams(AppLanguage.NETHERLANDS,"1234"), true),
                arrayOf(CollectionDetailParams(AppLanguage.ENGLISH,"SK-C-5"), true),
                arrayOf(CollectionDetailParams(AppLanguage.NETHERLANDS,"SK-l-89"), true),
            )
        }
    }
}
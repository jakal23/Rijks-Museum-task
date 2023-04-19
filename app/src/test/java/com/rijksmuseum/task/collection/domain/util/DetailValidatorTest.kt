package com.rijksmuseum.task.collection.domain.util

import com.google.common.truth.Truth
import com.rijksmuseum.task.collection.domain.model.detail.CollectionDetailParamsModel
import com.rijksmuseum.task.util.network.AppLanguage
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DetailValidatorTest(
    private val data: CollectionDetailParamsModel,
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
                arrayOf(CollectionDetailParamsModel(AppLanguage.ENGLISH, ""), false),
                arrayOf(CollectionDetailParamsModel(AppLanguage.ENGLISH, "a"), true),
                arrayOf(CollectionDetailParamsModel(AppLanguage.ENGLISH, "test"), true),
                arrayOf(CollectionDetailParamsModel(AppLanguage.ENGLISH, "1234"), true),
                arrayOf(CollectionDetailParamsModel(AppLanguage.NETHERLANDS, ""), false),
                arrayOf(CollectionDetailParamsModel(AppLanguage.NETHERLANDS, "a"), true),
                arrayOf(CollectionDetailParamsModel(AppLanguage.NETHERLANDS, "test"), true),
                arrayOf(CollectionDetailParamsModel(AppLanguage.NETHERLANDS, "1234"), true),
                arrayOf(CollectionDetailParamsModel(AppLanguage.ENGLISH, "SK-C-5"), true),
                arrayOf(CollectionDetailParamsModel(AppLanguage.NETHERLANDS, "SK-l-89"), true),
            )
        }
    }
}
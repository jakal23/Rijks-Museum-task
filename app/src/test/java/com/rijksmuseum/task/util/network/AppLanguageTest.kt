package com.rijksmuseum.task.util.network

import com.google.common.truth.Truth
import org.junit.Test

class AppLanguageTest {

    @Test
    fun `verify app support languages`() {
        val lnCodes = AppLanguage.values().map { it.code }

        Truth.assertThat(lnCodes)
            .containsAtLeast("nl", "en")
    }
}
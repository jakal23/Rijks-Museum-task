package com.rijksmuseum.task.collection.presentation.util

import com.google.common.truth.Truth
import com.rijksmuseum.task.collection.domain.model.WebImage
import com.rijksmuseum.task.collection.domain.model.detail.ArtObjectDetail
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ConverterKtTest {

    private val artObject = Mockito.mock(ArtObjectDetail::class.java)
    private val webImage = Mockito.mock(WebImage::class.java)

    @Before
    fun setUp() {
        Mockito.`when`(artObject.id).thenAnswer { "id" }
        Mockito.`when`(artObject.title).thenAnswer { "title" }
        Mockito.`when`(artObject.description).thenAnswer { "description" }
        Mockito.`when`(artObject.principalMaker).thenAnswer { "Maker" }
        Mockito.`when`(artObject.hasImage).thenAnswer { true }
        Mockito.`when`(artObject.showImage).thenAnswer { true }
        Mockito.`when`(artObject.webImage).thenAnswer { webImage }

        Mockito.`when`(webImage.url).thenAnswer { "http://test" }
    }

    @Test
    fun `convert art objet then verify result`() {
        val converted = artObject.toCollectionDetail()

        Truth.assertThat(converted.id).isEqualTo("id")
        Truth.assertThat(converted.title).isEqualTo("title")
        Truth.assertThat(converted.description).isEqualTo("description")
        Truth.assertThat(converted.author).isEqualTo("Maker")
        Truth.assertThat(converted.image).isEqualTo("http://test")
    }

    @Test
    fun `convert if art objet hasImage is false then result image is null`() {
        Mockito.`when`(artObject.hasImage).thenAnswer { false }

        val converted = artObject.toCollectionDetail()

        Truth.assertThat(converted.image).isNull()
    }

    @After
    fun tearDown() {
        Mockito.reset(artObject)
        Mockito.reset(webImage)
    }
}
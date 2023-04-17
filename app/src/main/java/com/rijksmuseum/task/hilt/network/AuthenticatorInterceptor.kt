package com.rijksmuseum.task.hilt.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticatorInterceptor : Interceptor {

    /**
     * Interceptor class for setting of the authorization query param for every request
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val url = request.url.newBuilder()
            .addQueryParameter(AUTHORIZATION_KEY, AUTHORIZATION_VALUE)
            .build()

        request = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }

    companion object {
        const val AUTHORIZATION_VALUE = "0fiuZFh4"
        const val AUTHORIZATION_KEY = "key"
    }
}
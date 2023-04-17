package com.rijksmuseum.task.hilt.network


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    @ViewModelScoped
    @BaseRetrofit
    fun retrofit(
        client: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("$BASE_URL/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @ViewModelScoped
    fun okHttpClient(
        logger: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(AuthenticatorInterceptor())
            .readTimeout(REQUEST_TIME_OUT_INTERVAL, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    @ViewModelScoped
    fun okHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
        }
    }

    @Provides
    @ViewModelScoped
    fun gson(): Gson {
        return GsonBuilder().create()
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseRetrofit

    private const val REQUEST_TIME_OUT_INTERVAL = 10L //seconds
    private const val BASE_URL = "https://www.rijksmuseum.nl"
}
package com.rba.hilttest.data.di

import com.rba.hilttest.data.api.MovieApiModule
import com.rba.hilttest.data.util.API_KEY
import com.rba.hilttest.data.util.BASE_URL_API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [MovieApiModule::class])
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideRequestInterceptor() = Interceptor {
        val url = it.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()
        val request = it.request()
            .newBuilder()
            .url(url)
            .build()
        return@Interceptor it.proceed(request)
    }

    @Provides
    fun provideClient(
        requestInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ) =
        OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL_API)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}
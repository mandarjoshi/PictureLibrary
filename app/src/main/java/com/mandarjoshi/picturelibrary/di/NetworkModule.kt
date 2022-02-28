package com.mandarjoshi.picturelibrary.di

import com.mandarjoshi.picturelibrary.BuildConfig
import com.mandarjoshi.picturelibrary.HeaderInterceptor
import dagger.Module
import retrofit2.converter.moshi.MoshiConverterFactory

import com.squareup.moshi.Moshi

import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
class NetworkModule {
    @Provides
    fun providesRetrofit(
        moshiConverterFactory: MoshiConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    fun providesOkHttpClient(headerInterceptor: HeaderInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(headerInterceptor).build()

    @Provides
    fun providesMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }

    @Provides
    fun providesHeaderInterceptor() : HeaderInterceptor {
        return HeaderInterceptor()
    }
}

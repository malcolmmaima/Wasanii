package com.todoist_android.data.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.tengenezalabs.wasanii.BuildConfig
import com.tengenezalabs.wasanii.data.api.EventsApi
import com.tengenezalabs.wasanii.data.respository.EventsRepository
import com.tengenezalabs.wasanii.data.respository.EventsRepositoryImpl
import com.tengenezalabs.wasanii.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesOkhttp(): OkHttpClient {
        val okhhtp = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor()
            logger.setLevel(HttpLoggingInterceptor.Level.BODY)

            okhhtp.addInterceptor(logger)
        }
        return okhhtp.build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()

    @Singleton
    @Provides
    fun apiService(retrofit: Retrofit): EventsApi =
        retrofit.create(EventsApi::class.java)


    @Singleton
    @Provides
    fun provideEventsRepo(eventsApi: EventsApi): EventsRepository = EventsRepositoryImpl(eventsApi)
}
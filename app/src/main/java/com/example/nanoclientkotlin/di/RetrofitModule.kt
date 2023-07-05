package com.example.nanoclientkotlin.di
import com.example.nanoclientkotlin.consts.ConstsCommSvc.Companion.BASE_URL
import com.example.nanoclientkotlin.dataRemote.MessageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.text.Typography.dagger

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Singleton
    @Provides
    fun provideGameApi(retrofit: Retrofit): MessageApi {
        return retrofit.create(MessageApi::class.java)
    }

}
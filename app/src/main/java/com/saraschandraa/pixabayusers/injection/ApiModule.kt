package com.saraschandraa.pixabayusers.injection

import com.saraschandraa.pixabayusers.webservice.ApiService
import com.saraschandraa.pixabayusers.webservice.PixbayAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val BASE_URL = "https://pixabay.com"

    @Provides
    fun getAPIHandler(): PixbayAPI {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(PixbayAPI::class.java)
    }

    @Provides
    open fun providesApiService(): ApiService {
        return ApiService()
    }
}
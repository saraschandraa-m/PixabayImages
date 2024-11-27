package com.saraschandraa.pixabayusers.webservice

import com.saraschandraa.pixabayusers.model.Pixabays
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PixbayAPI {

    @GET("api")
    fun getValues(@QueryMap params: Map<String, String>) : Single<Pixabays>
}
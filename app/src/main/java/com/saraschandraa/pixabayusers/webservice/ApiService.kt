package com.saraschandraa.pixabayusers.webservice

import com.saraschandraa.pixabayusers.injection.DaggerApiComponent
import com.saraschandraa.pixabayusers.model.Pixabays
import io.reactivex.Single
import javax.inject.Inject

class ApiService {

    @Inject
    lateinit var api: PixbayAPI

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getValues(params: Map<String, String>): Single<Pixabays> {
        return api.getValues(params)
    }
}
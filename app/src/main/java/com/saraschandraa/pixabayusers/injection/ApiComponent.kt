package com.saraschandraa.pixabayusers.injection

import com.saraschandraa.pixabayusers.webservice.ApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: ApiService)
}
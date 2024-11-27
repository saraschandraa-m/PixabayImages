package com.saraschandraa.pixabayusers.injection

import com.saraschandraa.pixabayusers.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface UserListViewModelComponent {

    fun inject(viewModel: ListViewModel)
}
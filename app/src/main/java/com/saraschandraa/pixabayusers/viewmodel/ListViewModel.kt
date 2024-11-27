package com.saraschandraa.pixabayusers.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.saraschandraa.pixabayusers.injection.DaggerUserListViewModelComponent
import com.saraschandraa.pixabayusers.model.Pixabays
import com.saraschandraa.pixabayusers.model.Sports
import com.saraschandraa.pixabayusers.webservice.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {


    val sportsLists by lazy { MutableLiveData<List<Sports>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var apiService: ApiService

    init {
        DaggerUserListViewModelComponent.create().inject(this)
    }


    fun getValues(page: Int) {

        val queries: MutableMap<String, String> = HashMap()

        queries["key"] = "47278305-2c896637143b21ce72de49c83"
        queries["q"] = "sports"
        queries["per_page"] = "10"
        queries["page"] = page.toString()


        disposable.add(
            apiService.getValues(queries)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Pixabays>() {
                    override fun onSuccess(t: Pixabays) {
                        loadError.value = false
                        loading.value = false
                        sportsLists.value = t.hits
                    }

                    override fun onError(e: Throwable) {
                        e.localizedMessage?.let { Log.e("Excception", it) }
                        loadError.value = true
                        loading.value = false
                        sportsLists.value = null
                    }

                })
        )
    }
}
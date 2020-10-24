package com.example.wiproprojectassignment.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.wiproprojectassignment.di.MyApplication
import com.example.wiproprojectassignment.model.CountryInfoListModel
import com.example.wiproprojectassignment.remote.RemoteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.NotNull
import javax.inject.Inject


class CountryInfoListViewModel(@NotNull appContext: Application) : AndroidViewModel(appContext) {

    @set:Inject
    lateinit var remoteRepository: RemoteRepository

    private lateinit var disposable: Disposable

    var list = MutableLiveData<CountryInfoListModel>()
    var toastMsg = MutableLiveData<String>()
    var showLoader = MutableLiveData<Boolean>()

    init {
        MyApplication.myComponent?.inject(this)
    }

    fun fetchData() {
        showLoader.value = true
        disposable = remoteRepository.getCountryInfoListData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                list.value = it
                showLoader.value = false
            }, {
                showLoader.value = false
                toastMsg.value = it.message
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
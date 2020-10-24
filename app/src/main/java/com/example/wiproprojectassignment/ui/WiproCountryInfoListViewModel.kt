package com.example.wiproprojectassignment.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wiproprojectassignment.di.WiproApplication
import com.example.wiproprojectassignment.model.WiproCountryInfoListModel
import com.example.wiproprojectassignment.remote.WiproRemoteRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.NotNull
import javax.inject.Inject


class WiproCountryInfoListViewModel() : ViewModel() {

    @set:Inject
    lateinit var wiproRemoteRepository: WiproRemoteRepository

    lateinit var disposable: Disposable

    var list = MutableLiveData<WiproCountryInfoListModel>()
    var toastMsg = MutableLiveData<String>()
    var showLoader = MutableLiveData<Boolean>()

    init {
        WiproApplication.wiproAppComponent?.inject(this)
    }

    fun fetchData() {
        showLoader.value = true
        disposable = wiproRemoteRepository.getCountryInfoListData()
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
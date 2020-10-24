package com.example.wiproprojectassignment.remote
import com.example.wiproprojectassignment.di.MyApplication
import com.example.wiproprojectassignment.model.CountryInfoListModel
import io.reactivex.Observable
import javax.inject.Inject

class RemoteRepository @Inject constructor() : APICallback {

    @Inject
    lateinit var apiCall: APICall

    init {
        MyApplication.myComponent.inject(this)
    }

    override fun getCountryInfoListData(): Observable<CountryInfoListModel> {
        return apiCall.getCountryInfoData()
    }
}

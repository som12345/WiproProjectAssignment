package com.example.wiproprojectassignment.remote
import com.example.wiproprojectassignment.di.WiproApplication
import com.example.wiproprojectassignment.model.WiproCountryInfoListModel
import io.reactivex.Observable
import javax.inject.Inject

class WiproRemoteRepository @Inject constructor() : WiproAPICallback {

    @Inject
    lateinit var wiproApiCall: WiproAPICall

    init {
        WiproApplication.wiproAppComponent.inject(this)
    }

    override fun getCountryInfoListData(): Observable<WiproCountryInfoListModel> {
        return wiproApiCall.getCountryInfoData()
    }
}

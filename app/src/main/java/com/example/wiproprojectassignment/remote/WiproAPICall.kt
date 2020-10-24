package com.example.wiproprojectassignment.remote

import com.example.wiproprojectassignment.model.WiproCountryInfoListModel
import io.reactivex.Observable
import retrofit2.http.GET

interface WiproAPICall {

    @GET("s/2iodh4vg0eortkl/facts.json")
    fun getCountryInfoData(): Observable<WiproCountryInfoListModel>

}

package com.example.wiproprojectassignment.remote

import com.example.wiproprojectassignment.model.CountryInfoListModel
import io.reactivex.Observable
import retrofit2.http.GET

interface APICall {

    @GET("s/2iodh4vg0eortkl/facts.json")
    fun getCountryInfoData(): Observable<CountryInfoListModel>

}

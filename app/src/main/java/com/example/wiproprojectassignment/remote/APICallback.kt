package com.example.wiproprojectassignment.remote

import com.example.wiproprojectassignment.model.CountryInfoListModel
import io.reactivex.Observable

interface APICallback {

    fun getCountryInfoListData() : Observable<CountryInfoListModel>
}
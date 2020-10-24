package com.example.wiproprojectassignment.remote

import com.example.wiproprojectassignment.model.WiproCountryInfoListModel
import io.reactivex.Observable

interface WiproAPICallback {
    fun getCountryInfoListData() : Observable<WiproCountryInfoListModel>
}
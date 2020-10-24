package com.example.wiproprojectassignment.di

import com.example.wiproprojectassignment.remote.RemoteRepository
import com.example.wiproprojectassignment.ui.CountryInfoListViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface MyComponent {

    fun inject(countryInfoListViewModel: CountryInfoListViewModel)
    //New code for retrofit injection
    fun inject(remoteRepository: RemoteRepository)
}

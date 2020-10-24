package com.example.wiproprojectassignment.di

import com.example.wiproprojectassignment.remote.WiproRemoteRepository
import com.example.wiproprojectassignment.ui.WiproCountryInfoListViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface WiproAppComponent {

    /**
     * Injected dagger
     */
    fun inject(wiproCountryInfoListViewModel: WiproCountryInfoListViewModel)
    //New code for retrofit injection
    fun inject(wiproRemoteRepository: WiproRemoteRepository)
}

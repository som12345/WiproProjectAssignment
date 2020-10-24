package com.example.wiproprojectassignment.di

import com.example.wiproprojectassignment.remote.RemoteRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface MyComponent {

    fun inject(model: CountryInfoListViewModel)
    //New code for retrofit injection
    fun inject(repository: RemoteRepository)
}

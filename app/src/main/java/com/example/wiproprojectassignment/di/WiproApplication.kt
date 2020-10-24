package com.example.wiproprojectassignment.di

import androidx.multidex.MultiDexApplication

/**
 * Application class
 */
class WiproApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        wiproAppComponent = DaggerWiproAppComponent.builder().appModule(AppModule()).build()
    }

    companion object {
         var wiproAppComponent: WiproAppComponent? = null
    }
}

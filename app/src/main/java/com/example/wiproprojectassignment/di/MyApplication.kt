package com.example.wiproprojectassignment.di

import androidx.multidex.MultiDexApplication

class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        myComponent = DaggerMyComponent.builder().appModule(AppModule()).build()
    }

    companion object {
        lateinit var myComponent: MyComponent
    }
}

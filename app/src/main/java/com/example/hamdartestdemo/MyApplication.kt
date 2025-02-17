package com.example.hamdartestdemo

import android.app.Application
import com.example.hamdartestdemo.di.AppContextProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppContextProvider.initialize(this)
    }

}
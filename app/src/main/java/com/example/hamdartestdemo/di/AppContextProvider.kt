package com.example.hamdartestdemo.di

import android.app.Application
import android.content.Context

object AppContextProvider {
    private lateinit var application: Application

    fun initialize(app: Application) {
        application = app
    }

    fun getContext(): Context {
        return application.applicationContext
    }
}

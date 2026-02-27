package com.example.resourcehub

import android.app.Application

class ResourceHubApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppData.init(this)
    }
}

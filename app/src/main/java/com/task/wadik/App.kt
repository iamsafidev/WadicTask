package com.task.wadik

import android.app.Application
import android.content.Context
import com.task.wadik.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    companion object {
        var instance: App? = null
        fun getAppContext(): Context {
            return instance as Context
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initiateKoin()
    }

    private fun initiateKoin() {
        startKoin {
            androidContext(this@App)
            modules(provideDependency())
        }
    }

    private fun provideDependency() = appComponent
}
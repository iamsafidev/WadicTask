package com.task.wadik.base


import androidx.appcompat.app.AppCompatActivity
import com.task.wadik.utils.InternetMonitor

abstract class BaseActivity : AppCompatActivity() {
    private var isInternetAvailable: Boolean = false
    private val internetMonitor = InternetMonitor(object :
        InternetMonitor.OnInternetConnectivityListener {
        override fun onInternetAvailable() {
            isInternetAvailable = true
        }

        override fun onInternetLost() {
            isInternetAvailable = false
        }
    })

    override fun onStart() {
        super.onStart()
        internetMonitor.register(this)
    }

    override fun onPause() {
        super.onPause()
        internetMonitor.unregister(this)
    }

    fun getInternetConnectionStatus(): Boolean {
        return isInternetAvailable
    }

}
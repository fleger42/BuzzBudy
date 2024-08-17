package com.example.buzzbuddy;
import SampleLifecycleListener
import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner

class MyApplication : Application() {

    private val lifecycleListener: SampleLifecycleListener by lazy {
        SampleLifecycleListener(this)
    }

    override fun onCreate() {
        super.onCreate()
        setupLifecycleListener()
    }

    private fun setupLifecycleListener() {
        //ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleListener)
    }
}
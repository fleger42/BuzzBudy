package com.example.buzzbuddy;
import SampleLifecycleListener
import android.app.Application
import android.content.IntentFilter
import android.provider.Telephony
import androidx.lifecycle.ProcessLifecycleOwner

class MyApplication : Application() {

    private val lifecycleListener: SampleLifecycleListener by lazy {
        SampleLifecycleListener(this)
    }

    override fun onCreate() {
        setupLifecycleListener()
        registerReceiver(SmsReceiver, IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
        super.onCreate()
    }

    private fun setupLifecycleListener() {
        //ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleListener)
    }
}
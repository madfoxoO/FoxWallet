package com.github.madfoxoo.foxwallet

import android.app.Application
import com.github.madfoxoo.foxwallet.di.AppComponent
import com.github.madfoxoo.foxwallet.di.ComponentHolder
import com.github.madfoxoo.foxwallet.di.DaggerAppComponent

class FoxWalletApplication : Application(), ComponentHolder {

    override lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(applicationContext)
            .build()
    }
}

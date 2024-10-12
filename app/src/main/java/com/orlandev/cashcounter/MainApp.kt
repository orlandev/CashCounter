package com.orlandev.cashcounter

import android.app.Application
import com.orlandev.cashcounter.di.appModule
import com.orlandev.cashcounter.di.dataRepositoryModule
import com.orlandev.cashcounter.di.databaseModule
import com.orlandev.cashcounter.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MainApp)
            modules(appModule, dataRepositoryModule, viewModelsModule, databaseModule)
        }
    }

}

//FIRMA APK_LIS
//A6:96:E7:55:54:26:C5:F7:12:DC:27:71:17:26:0F:26:61:C9:8C:82
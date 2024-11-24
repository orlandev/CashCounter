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
//7D:44:84:B4:AA:4A:06:69:3F:78:42:4E:F4:29:64:8D:98:C0:33:18
//alias orlando
//clave Qu35t64*05*L
package com.orlandev.cashcounter.di

import android.content.Context
import com.orlandev.cashcounter.data.DataRepository
import com.orlandev.cashcounter.data.database.CashCounterDatabase
import com.orlandev.cashcounter.data.database.dao.HistoryDao
import com.orlandev.cashcounter.ui.screens.HomeViewModel
import com.orlandev.cashcounter.utils.CashPrintImpl
import com.orlandev.cashcounter.utils.ICashPrint
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory<ICashPrint> {
        /*    (named("CashPrintImpl"))*/
        CashPrintImpl()
    }



    single {
        CashPrintImpl()
    }
}

val viewModelsModule = module {
    viewModel {
        HomeViewModel(get(), get())
    }
}

fun provideDatabase(appContext: Context) = CashCounterDatabase.getInstance(appContext)


fun provideHistoryDao(database: CashCounterDatabase): HistoryDao = database.historyDao()

val databaseModule = module {

    single {
        provideDatabase(get())
    }

    single {
        provideHistoryDao(get())
    }

}

val dataRepositoryModule = module {
    single {
        DataRepository(get())
    }
}
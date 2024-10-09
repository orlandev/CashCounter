package com.orlandev.cashcounter.di

import com.orlandev.cashcounter.ui.screens.HomeViewModel
import com.orlandev.cashcounter.utils.CashPrintImpl
import com.orlandev.cashcounter.utils.ICashPrint
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
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
        HomeViewModel(get())
    }
}
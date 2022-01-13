package com.inflames1986.janweatherkotlin.di

import com.inflames1986.janweatherkotlin.framework.ui.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    //ViewModels
    viewModel { MainViewModel() }
}
package com.inflames1986.janweatherkotlin.di

import com.inflames1986.janweatherkotlin.viewmodel.MainViewModel
import com.inflames1986.janweatherkotlin.model.repository.Repository
import com.inflames1986.janweatherkotlin.model.repository.RepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<Repository> { RepositoryImpl() }

    viewModel { MainViewModel(get()) }
}
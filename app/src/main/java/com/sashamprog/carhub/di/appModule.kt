package com.sashamprog.carhub.di

import com.sashamprog.carhub.data.api.CarHubApi
import com.sashamprog.carhub.data.api.CarHubApiImpl
import com.sashamprog.carhub.data.repository.UserRepositoryImpl
import com.sashamprog.carhub.domain.repository.UserRepository
import com.sashamprog.carhub.domain.usecase.LoginUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.sashamprog.carhub.ui.features.catalog.CatalogViewModel
import com.sashamprog.carhub.ui.features.account.AccountViewModel
import com.sashamprog.carhub.ui.features.auth.AuthViewModel

val appModule = module {
    singleOf(::UserRepositoryImpl) bind UserRepository::class
    single { CarHubApiImpl(get()) }
    single {
        Retrofit.Builder()
            .baseUrl("https://api.carhub.com/") // Replace with your API's base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarHubApi::class.java)
    }
    single { LoginUseCase(get()) }
    viewModelOf(::CatalogViewModel)
    viewModelOf(::AccountViewModel)
    viewModelOf(::AuthViewModel)
}
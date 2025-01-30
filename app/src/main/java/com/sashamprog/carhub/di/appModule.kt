package com.sashamprog.carhub.di

import android.content.Context
import com.sashamprog.carhub.data.repository.CarRepositoryImpl
import com.sashamprog.carhub.data.source.CarHubApi
import com.sashamprog.carhub.data.repository.UserRepositoryImpl
import com.sashamprog.carhub.data.source.CarDataSource
import com.sashamprog.carhub.data.source.LocalCarDataSourceImpl
import com.sashamprog.carhub.data.source.LocalUserDataSourceImpl
import com.sashamprog.carhub.data.source.UserDataSource
import com.sashamprog.carhub.domain.repository.CarRepository
import com.sashamprog.carhub.domain.repository.UserRepository
import com.sashamprog.carhub.domain.usecase.LoginUseCase
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.sashamprog.carhub.ui.features.catalog.CatalogViewModel
import com.sashamprog.carhub.ui.features.create_car.CreateEditCarViewModel
import com.sashamprog.carhub.ui.features.account.AccountViewModel
import com.sashamprog.carhub.ui.features.auth.AuthViewModel
import org.koin.android.ext.koin.androidContext

val appModule = module {
    singleOf(::UserRepositoryImpl) bind UserRepository::class
    singleOf(::LocalUserDataSourceImpl) bind UserDataSource::class
//    singleOf(::RemoteUserDataSourceImpl) bind UserDataSource::class
//    single<UserDataSource> { RemoteUserDataSourceImpl(get()) }

    singleOf(::CarRepositoryImpl) bind CarRepository::class
    singleOf(::LocalCarDataSourceImpl) bind CarDataSource::class
//    singleOf(::RemoteCarDataSourceImpl) bind CarDataSource::class

    single<CarRepository> { CarRepositoryImpl(get()) }

    single { androidContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE) }
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
    viewModelOf(::CreateEditCarViewModel)
}
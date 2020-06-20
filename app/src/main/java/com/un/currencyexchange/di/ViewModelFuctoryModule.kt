package com.un.currencyexchange.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ViewModelFuctoryModule {
    @Singleton
    @Binds
    abstract fun bindVMFuctory(modelProviderFuctory: ViewModelProviderFactory) : ViewModelProvider.Factory
}

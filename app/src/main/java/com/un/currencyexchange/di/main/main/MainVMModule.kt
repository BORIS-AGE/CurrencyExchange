package com.un.currencyexchange.di.main.main

import androidx.lifecycle.ViewModel
import com.un.currencyexchange.ui.home.HomeViewModel
import com.un.currencyexchange.di.VMKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainVMModule {

    @Binds
    @IntoMap
    @VMKey(HomeViewModel::class)
    abstract fun postListViewModel(viewModel: HomeViewModel): ViewModel

}
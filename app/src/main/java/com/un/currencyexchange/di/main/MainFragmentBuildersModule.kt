package com.un.currencyexchange.di.main

import com.un.currencyexchange.ui.home.HomeFragment
import com.un.currencyexchange.di.main.main.MainVMModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector(modules = [MainVMModule::class])
    abstract fun contributeHomeFragment(): HomeFragment


}

package com.un.currencyexchange.di

import com.un.currencyexchange.MainActivity
import com.un.currencyexchange.di.main.MainFragmentBuildersModule
import com.un.currencyexchange.di.main.MainModule
import com.un.currencyexchange.di.main.MainScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @MainScope
    @ContributesAndroidInjector(
        modules = arrayOf(
            MainFragmentBuildersModule::class,
            MainModule::class
        )
    )
    abstract fun contributeMainActivity(): MainActivity
}

package com.un.currencyexchange.di

import android.app.Application
import android.content.SharedPreferences
import com.un.currencyexchange.BaseApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidInjectionModule::class, // must be replaced with AndroidInjectionModule
        ActivityBuilderModule::class,
        AppModule::class
    )
)
interface AppComponent : AndroidInjector<BaseApplication> {

    fun sharedPreferences(): SharedPreferences

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }
}
package com.un.currencyexchange.di.main

import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.un.currencyexchange.MainActivity
import com.un.currencyexchange.R
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @MainScope
    @Provides
    fun provideNavigationController(mainActivity: MainActivity): NavController {
        return Navigation.findNavController(mainActivity, R.id.nav_host_fragment)
    }
}

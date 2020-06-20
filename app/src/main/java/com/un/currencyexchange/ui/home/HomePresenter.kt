package com.un.currencyexchange.ui.home

import androidx.lifecycle.MutableLiveData
import com.un.currencyexchange.models.AdapterItem
import com.un.currencyexchange.models.MainErrorModel
import com.un.currencyexchange.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class HomePresenter @Inject constructor(val api: Api){

    private lateinit var callback: HomeView

    fun loadCurrencies() {
        val disposable = api.getCurrencies()
            .subscribeOn(Schedulers.io())
            .map { it.rates.map { AdapterItem(Pair(it.key, it.value)) } }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onDataLoad(it)
                android.os.Handler().postDelayed({loadCurrencies()}, 1800000)
            },{
                if (it is MainErrorModel)
                    callback.onError(it.text)
            })

    }

    fun setCallback(homeFragment: HomeView) {
        callback = homeFragment
    }
}
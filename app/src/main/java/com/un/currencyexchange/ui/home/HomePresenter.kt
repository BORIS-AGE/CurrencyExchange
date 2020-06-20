package com.un.currencyexchange.ui.home

import com.un.currencyexchange.models.AdapterItem
import com.un.currencyexchange.models.MainErrorModel
import com.un.currencyexchange.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenter @Inject constructor(val api: Api){

    private lateinit var callback: HomeView
    val disposable = mutableListOf<Disposable>()

    fun loadCurrencies() {
        disposable.add(api.getCurrencies()
            .subscribeOn(Schedulers.io())
            .map { it.rates.map { AdapterItem(Pair(it.key, it.value)) } }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                callback.onDataLoad(it)
                android.os.Handler().postDelayed({loadCurrencies()}, 1800000)
            },{
                if (it is MainErrorModel)
                    callback.onError(it.text)
            }))

    }

    fun setCallback(homeFragment: HomeView) {
        callback = homeFragment
    }
}
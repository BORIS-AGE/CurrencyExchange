package com.un.currencyexchange.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.un.currencyexchange.models.AdapterItem
import com.un.currencyexchange.models.MainErrorModel
import com.un.currencyexchange.network.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(val api: Api) : ViewModel() {

    val rvItems = MutableLiveData<List<AdapterItem>>()
    val error = MutableLiveData<String>()

    fun loadCurrencies() {
        val disposable = api.getCurrencies()
            .subscribeOn(Schedulers.io())
            .map { it.rates.map { AdapterItem(Pair(it.key, it.value)) } }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                rvItems.value = it
              //  android.os.Handler().postDelayed({loadCurrencies()}, 1000)
            },{
                if (it is MainErrorModel)
                    error.value = it.text
            })

    }
}
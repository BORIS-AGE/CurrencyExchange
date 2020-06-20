package com.un.currencyexchange.ui.home

import com.un.currencyexchange.models.AdapterItem

interface HomeView {
    fun onDataLoad(list: List<AdapterItem>)
    fun onError(str: String)
}
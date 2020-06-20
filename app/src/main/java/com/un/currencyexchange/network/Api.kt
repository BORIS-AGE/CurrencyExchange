package com.un.currencyexchange.network

import com.un.currencyexchange.models.Currency
import io.reactivex.Observable
import retrofit2.http.GET

interface Api {
    @GET("latest")
    fun getCurrencies(): Observable<Currency>
}
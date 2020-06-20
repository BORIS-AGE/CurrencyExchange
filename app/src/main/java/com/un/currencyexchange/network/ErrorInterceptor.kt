package com.un.currencyexchange.network

import android.util.Log
import com.google.gson.Gson
import com.un.currencyexchange.models.MainErrorModel
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

internal class ErrorInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        if (!response.isSuccessful) {
            parseErrorBody(response)
        }
        return response
    }

    @Throws(IOException::class)
    private fun parseErrorBody(response: Response) { //temp kostul, change when backend start sending formal model of error
        val errorBody: String = response.body!!.string()
        Log.e("SERVER RESPONSE ERROR", errorBody)
        val errorResponse: MainErrorModel = Gson().fromJson(errorBody, MainErrorModel::class.java)
        throw errorResponse
    }
}

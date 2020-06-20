package com.un.currencyexchange.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.un.currencyexchange.R
import com.un.currencyexchange.models.AdapterItem
import org.json.JSONArray

class Util {
    companion object{
        fun formatString(str: String, size: Int): String{
            var txt = str
            if (txt.length > size)
                txt = txt.substring(0, size - 1)
            return txt
        }

        @Synchronized
        fun exchangeMoney(sharedPreferences: SharedPreferences, key1: String, key2: String, value: Double, curr1: Double, curr2: Double, context: Context): String{
            val list = arrayListOf<AdapterItem>()
            val jsonArray = JSONArray(sharedPreferences.getString(Constants.SHARED_KEY, "{}"))
            if (jsonArray.length() > 0)
                for (i in 0 until jsonArray.length()) {
                    list.add(Gson().fromJson(jsonArray[i].toString(), AdapterItem::class.java))
                }

            val exchange = value * curr2 /curr1
            val index1 = list.map { it.data.first }.indexOf(key1)
            val index2 = list.map { it.data.first }.indexOf(key2)
            if (list[index1].data.second >= value) {
                list[index1] =
                    AdapterItem(Pair(list[index1].data.first, list[index1].data.second - value))
                list[index2] =
                    AdapterItem(Pair(list[index2].data.first, list[index2].data.second + exchange))


                sharedPreferences.edit().putString(Constants.SHARED_KEY, Gson().toJson(list))
                    .apply()
                return "${list[index1].data.first} - ${list[index1].data.second}\n${list[index2].data.first} - ${list[index2].data.second}"
            }else{
                return context.getString(R.string.u_dont_have_money)
            }
        }

        @Synchronized
        fun fillSharedPreferences(sharedPreferences: SharedPreferences, list: List<AdapterItem>) {
            if (sharedPreferences.getString(Constants.SHARED_KEY, "").isNullOrEmpty()){
                sharedPreferences.edit().putString(Constants.SHARED_KEY, Gson().toJson(list.map { AdapterItem(Pair(it.data.first, 100.0)) })).apply()
            }
        }

        fun getMyMoney(sharedPreferences: SharedPreferences, key: String): String {
            val list = arrayListOf<AdapterItem>()
            val jsonArray = JSONArray(sharedPreferences.getString(Constants.SHARED_KEY, "{}"))
            if (jsonArray.length() > 0)
                for (i in 0 until jsonArray.length()) {
                    list.add(Gson().fromJson(jsonArray[i].toString(), AdapterItem::class.java))
                }
            val index = list.map { it.data.first }.indexOf(key)
            return formatString(list[index].data.second.toString(), 7)
        }
    }
}
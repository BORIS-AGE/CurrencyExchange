package com.un.currencyexchange.util

class Util {
    companion object{
        fun formatString(str: String, size: Int): String{
            var txt = str
            if (txt.length > size)
                txt = txt.substring(0, size - 1)
            return txt
        }
    }
}
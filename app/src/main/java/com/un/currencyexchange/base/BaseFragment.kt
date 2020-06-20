package com.un.currencyexchange.base

import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment

open class BaseFragment: DaggerFragment() {
    protected fun showMessage(str: String){
        view?.let {
            Snackbar.make(it, str, Snackbar.LENGTH_LONG).show()
        }
    }
}
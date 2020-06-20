package com.un.currencyexchange.ui.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.un.currencyexchange.R
import com.un.currencyexchange.models.AdapterItem
import com.un.currencyexchange.util.Util
import kotlinx.android.synthetic.main.currency_item_top.view.*

class CurrencyAdapter constructor(private val list: ArrayList<AdapterItem>, private var oponent: AdapterItem, private val listener: OnCurrencyValueEdit?
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyVH>() {

    private var comparedValue = 1.0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyVH = CurrencyVH(
        LayoutInflater.from(parent.context).inflate(R.layout.currency_item_top, parent, false),
        listener
    )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CurrencyVH, position: Int) {
        holder.bind(list[position], oponent, comparedValue)
    }

    fun setData(newList: List<AdapterItem>) {
        val diffCallback = CurrencyDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setComparableWith(activeBot: Int, adapterItem: AdapterItem) {
        oponent = adapterItem
        notifyItemRangeChanged(0, list.size - 1)
        comparedValue = 1.0
    }

    fun setNewPrice(compareTo: Double, position: Int) {
        if (compareTo != comparedValue) {
            comparedValue = compareTo
            notifyItemChanged(position)
        }
    }

    class CurrencyVH constructor(private val view: View, private val listener: OnCurrencyValueEdit?) : RecyclerView.ViewHolder(view) {
        fun bind(item: AdapterItem, oponent: AdapterItem, compareTo: Double) {
            view.tvCurrency.text = item.data.first
            view.tvDESC.text = "1 ${item.data.first} = ${Util.formatString((oponent.data.second / item.data.second).toString(), 6)} ${oponent.data.first}"
            if (listener != null) {
                view.etPrice.setText("1")
                view.etPrice.doOnTextChanged { text, _, _, _ ->
                    if ("([\\.0-9]+)".toRegex().matches(text.toString())) {
                        listener.onTextChanged(text.toString())
                    }
                }
            } else {
                view.etPrice.isEnabled = false
                view.etPrice.setText(Util.formatString((compareTo * item.data.second / oponent.data.second).toString(), 6))
            }
        }
    }

    interface OnCurrencyValueEdit {
        fun onTextChanged(str: String)
    }
}
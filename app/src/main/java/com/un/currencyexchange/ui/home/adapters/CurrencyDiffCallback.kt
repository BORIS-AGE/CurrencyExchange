package com.un.currencyexchange.ui.home.adapters

import androidx.recyclerview.widget.DiffUtil
import com.un.currencyexchange.models.AdapterItem
import com.un.currencyexchange.models.Currency

class CurrencyDiffCallback (private val oldList: List<AdapterItem>, private val newList: List<AdapterItem>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].data.first == newList[newItemPosition].data.first

    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean
        = oldList[oldPosition].data.first == newList[newPosition].data.first && oldList[oldPosition].data.second == newList[newPosition].data.second


    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
        return super.getChangePayload(oldPosition, newPosition)
    }
}
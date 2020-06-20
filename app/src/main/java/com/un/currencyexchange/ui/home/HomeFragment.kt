package com.un.currencyexchange.ui.home

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.un.currencyexchange.R
import com.un.currencyexchange.base.BaseFragment
import com.un.currencyexchange.models.AdapterItem
import com.un.currencyexchange.ui.home.adapters.CurrencyAdapter
import com.un.currencyexchange.util.Util
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : BaseFragment(), CurrencyAdapter.OnCurrencyValueEdit, HomeView,
    View.OnClickListener {

    @Inject
    lateinit var presenter: HomePresenter

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var adapter: CurrencyAdapter
    private lateinit var adapter2: CurrencyAdapter
    private var activeTop = 0
    private var activeBot = 0
    private val list = arrayListOf<AdapterItem>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setCallback(this)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvCurrencies)

        val snapHelper2 = PagerSnapHelper()
        snapHelper2.attachToRecyclerView(rvCurrencies2)

        tvExchange.setOnClickListener(this)

        presenter.loadCurrencies()
        addListeners()
    }

    private fun addListeners() {
        rvCurrencies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val temp = (rvCurrencies.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (temp != activeTop){
                    activeTop = temp
                    adapter2.setComparableWith(list[activeTop])
                    changeHeader()
                }
            }
        })
        rvCurrencies2.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val temp = (rvCurrencies2.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (temp != activeBot) {
                    activeBot = temp
                    adapter.setComparableWith(list[activeBot])
                    changeHeader()
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun changeHeader() {
        tvHeader.text = "1 ${list[activeTop].data.first} = ${Util.formatString(
            (list[activeTop].data.second / list[activeBot].data.second).toString(), 6
        )} ${list[activeBot].data.first}"
    }

    override fun onTextChanged(str: String) {
        adapter2.setNewPrice(str.toDouble(), activeBot)
    }

    override fun onDataLoad(list: List<AdapterItem>) {
        this.list.clear()
        this.list.addAll(list)
        if (rvCurrencies.adapter == null) {
            adapter = CurrencyAdapter(arrayListOf(), list[0], this, sharedPreferences)
            adapter2 = CurrencyAdapter(arrayListOf(), list[0], null, sharedPreferences)
            rvCurrencies.adapter = adapter
            rvCurrencies2.adapter = adapter2
            changeHeader()
            Util.fillSharedPreferences(sharedPreferences, list)
        }
        adapter.setData(list)
        adapter2.setData(list)
    }

    override fun onError(str: String) {
        showMessage(str)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvExchange -> {
                showMessage(Util.exchangeMoney(
                    sharedPreferences,
                    list[activeTop].data.first,
                    list[activeBot].data.first,
                    adapter2.comparedValue,
                    list[activeTop].data.second,
                    list[activeBot].data.second,
                    context!!
                ))
                adapter2.notifyItemChanged(activeBot)
                adapter.notifyItemChanged(activeTop)
            }
        }
    }

    override fun onDestroy() {
        presenter.disposable.forEach { it.dispose() }
        super.onDestroy()
    }

}

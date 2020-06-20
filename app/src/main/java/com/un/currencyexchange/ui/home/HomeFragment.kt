package com.un.currencyexchange.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.un.currencyexchange.R
import com.un.currencyexchange.base.BaseFragment
import com.un.currencyexchange.di.ViewModelProviderFactory
import com.un.currencyexchange.ui.home.adapters.CurrencyAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : BaseFragment(), CurrencyAdapter.OnCurrencyValueEdit {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: CurrencyAdapter
    private lateinit var adapter2: CurrencyAdapter
    private var activeTop = 0
    private var activeBot = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(HomeViewModel::class.java)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvCurrencies)

        val snapHelper2 = PagerSnapHelper()
        snapHelper2.attachToRecyclerView(rvCurrencies2)

        viewModel.loadCurrencies()
        initObservables()
        addListeners()
    }

    private fun addListeners() {
        rvCurrencies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val temp = (rvCurrencies.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (temp != activeTop){
                    activeTop = temp
                    adapter2.setComparableWith(activeTop, viewModel.rvItems.value!![activeTop])
                }
            }
        })
        rvCurrencies2.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val temp = (rvCurrencies2.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (temp != activeBot) {
                    activeBot = temp
                    adapter.setComparableWith(activeBot, viewModel.rvItems.value!![activeBot])
                }
            }
        })
    }

    private fun initObservables() {
        viewModel.rvItems.observe(viewLifecycleOwner, Observer {
            if (rvCurrencies.adapter == null) {
                adapter = CurrencyAdapter(arrayListOf(), it[0], this)
                adapter2 = CurrencyAdapter(arrayListOf(), it[0], null)
                rvCurrencies.adapter = adapter
                rvCurrencies2.adapter = adapter2
            }
            adapter.setData(it)
            adapter2.setData(it)
        })
    }

    override fun onTextChanged(str: String) {
        adapter2.setNewPrice(str.toDouble(), activeBot)
    }

}

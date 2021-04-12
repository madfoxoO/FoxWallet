package com.github.madfoxoo.foxwallet.currencies.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.madfoxoo.foxwallet.core.recycler.DiffableListItem
import com.github.madfoxoo.foxwallet.core.recycler.DiffableListItemCallback
import com.github.madfoxoo.foxwallet.core.ui.view.NavigationBarWindowInsetsListener
import com.github.madfoxoo.foxwallet.core.ui.view.StatusBarWindowInsetsListener
import com.github.madfoxoo.foxwallet.currencies.list.recycler.NoCurrenciesAdapterDelegate
import com.github.madfoxoo.foxwallet.currencies.list.recycler.NoCurrenciesViewModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class CurrenciesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle),
    CurrenciesPresenter {

    @Suppress("UNCHECKED_CAST")
    private val currenciesAdapter: AsyncListDifferDelegationAdapter<DiffableListItem>
        get() = rvCurrencies.adapter as AsyncListDifferDelegationAdapter<DiffableListItem>

    private lateinit var btnBack: ImageButton
    private lateinit var rvCurrencies: RecyclerView


    init {
        StatusBarWindowInsetsListener.applyTo(this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        btnBack = findViewById(R.id.btn_back)

        rvCurrencies = findViewById(R.id.rv_currencies)
        with(rvCurrencies) {
            NavigationBarWindowInsetsListener.applyTo(this)
            layoutManager = LinearLayoutManager(context)
            val layoutInflater = LayoutInflater.from(context)
            adapter = AsyncListDifferDelegationAdapter(
                DiffableListItemCallback(),
                AdapterDelegatesManager(
                    NoCurrenciesAdapterDelegate(layoutInflater)
                )
            ).apply {
                items = listOf(NoCurrenciesViewModel)
            }
            setHasFixedSize(true)
        }
    }
}

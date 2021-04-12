package com.github.madfoxoo.foxwallet.currencies.list.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.madfoxoo.foxwallet.core.recycler.DiffableListItem
import com.github.madfoxoo.foxwallet.currencies.list.R
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class NoCurrenciesAdapterDelegate(private val layoutInflater: LayoutInflater) :
    AdapterDelegate<List<DiffableListItem>>() {

    override fun isForViewType(items: List<DiffableListItem>, position: Int): Boolean {
        return items[position] is NoCurrenciesViewModel
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return layoutInflater
            .inflate(R.layout.listitem_no_currencies, parent, false)
            .let { NoCurrenciesViewHolder(it) }
    }

    override fun onBindViewHolder(
        items: List<DiffableListItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
    }
}

private class NoCurrenciesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
}

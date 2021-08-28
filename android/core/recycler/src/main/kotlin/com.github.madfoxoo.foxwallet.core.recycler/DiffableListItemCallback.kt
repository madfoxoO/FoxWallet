package com.github.madfoxoo.foxwallet.core.recycler

import androidx.recyclerview.widget.DiffUtil

/**
 * Common implementation of [DiffUtil.ItemCallback] for [DiffableListItem].
 */
class DiffableListItemCallback : DiffUtil.ItemCallback<DiffableListItem>() {

    override fun areItemsTheSame(oldItem: DiffableListItem, newItem: DiffableListItem): Boolean {
        return oldItem.isTheSameAs(newItem)
    }

    override fun areContentsTheSame(oldItem: DiffableListItem, newItem: DiffableListItem): Boolean {
        return oldItem.hasTheSameContent(newItem)
    }

    override fun getChangePayload(oldItem: DiffableListItem, newItem: DiffableListItem): Any? {
        return oldItem.calculateChangePayload(newItem)
    }
}

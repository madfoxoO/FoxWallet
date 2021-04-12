package com.github.madfoxoo.foxwallet.core.recycler

import androidx.recyclerview.widget.RecyclerView

interface DiffableListItem : ListItem {

    /**
     * Indicates whether some other object represent the same item as this one.
     */
    fun isTheSameAs(other: DiffableListItem): Boolean {
        return this.javaClass == other.javaClass && this.itemId == other.itemId
    }

    /**
     * Indicates whether some other object has the same data as this one.
     */
    fun hasTheSameContent(other: DiffableListItem): Boolean {
        return this == other
    }

    /**
     * Returns a payload about change between the other object and this one.
     */
    fun calculateChangePayload(other: DiffableListItem): Any? {
        return null
    }
}
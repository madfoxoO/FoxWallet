package com.github.madfoxoo.foxwallet.core.recycler

import androidx.recyclerview.widget.RecyclerView

interface ListItem {
    val itemId: Long get() = RecyclerView.NO_ID
}

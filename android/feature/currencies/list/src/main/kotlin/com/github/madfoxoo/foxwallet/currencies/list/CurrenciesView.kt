package com.github.madfoxoo.foxwallet.currencies.list

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class CurrenciesView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle),
    CurrenciesPresenter

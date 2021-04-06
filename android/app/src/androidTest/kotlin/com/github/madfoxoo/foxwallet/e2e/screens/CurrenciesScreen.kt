package com.github.madfoxoo.foxwallet.screens

import com.agoda.kakao.common.views.KView
import com.github.madfoxoo.foxwallet.R
import com.github.madfoxoo.foxwallet.currencies.list.CurrenciesView
import com.kaspersky.kaspresso.screens.KScreen
import org.hamcrest.Matchers.equalTo

object CurrenciesScreen : KScreen<CurrenciesScreen>() {

    override val layoutId: Int
        get() = R.layout.rib_currencies

    override val viewClass: Class<*>
        get() = CurrenciesView::class.java

    val root: KView by lazy {
        KView {
            withClassName(equalTo(CurrenciesView::class.java.name))
        }
    }
}

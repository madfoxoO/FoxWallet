package com.github.madfoxoo.foxwallet.screens

import android.view.View
import com.agoda.kakao.common.views.KView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.text.KButton
import com.agoda.kakao.text.KTextView
import com.github.madfoxoo.foxwallet.R
import com.github.madfoxoo.foxwallet.currencies.list.CurrenciesView
import com.kaspersky.kaspresso.screens.KScreen
import org.hamcrest.Matcher
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

    val list: KRecyclerView by lazy {
        KRecyclerView(
            builder = {
                withId(R.id.rv_currencies)
            },
            itemTypeBuilder = {
                itemType { matcher -> NoCurrenciesItem(matcher) }
            }
        )
    }

    class NoCurrenciesItem(parent: Matcher<View>) : KRecyclerItem<NoCurrenciesItem>(parent) {
        val title: KTextView by lazy {
            KTextView {
                withId(R.id.tv_title)
                withParent { withId(R.id.ll_no_currencies) }
            }
        }

        val description: KTextView by lazy {
            KTextView {
                withId(R.id.tv_description)
            }
        }

        val add: KButton by lazy {
            KButton {
                withId(R.id.btn_add)
            }
        }
    }
}

package com.github.madfoxoo.foxwallet.root

import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.github.madfoxoo.foxwallet.currencies.list.CurrenciesBuilder
import com.github.madfoxoo.foxwallet.currencies.list.CurrenciesRouter
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link RootBuilder.RootScope}.
 */
@Mockable
class RootRouter(
    view: RootView,
    interactor: RootInteractor,
    component: RootBuilder.Component,
    private val currenciesBuilder: CurrenciesBuilder
) : ViewRouter<RootView, RootInteractor>(view, interactor, component) {

    private var currenciesRouter: CurrenciesRouter? = null

    fun attachCurrencies() {
        val router = currenciesBuilder.build(view)
        attachChild(router)
        view.addView(router.view)
        currenciesRouter = router
    }
}

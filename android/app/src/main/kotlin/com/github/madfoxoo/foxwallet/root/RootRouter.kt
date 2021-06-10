package com.github.madfoxoo.foxwallet.root

import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.github.madfoxoo.foxwallet.root.nav.NavigationBuilder
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link RootBuilder.RootScope}.
 */
@Mockable
class RootRouter internal constructor(
    view: RootView,
    interactor: RootInteractor,
    component: RootBuilder.Component,
    private val navigationBuilder: NavigationBuilder
) : ViewRouter<RootView, RootInteractor>(
    view,
    interactor,
    component
) {

    fun attachNavigation() {
        val router = navigationBuilder.build(view)
        attachChild(router)
        view.addView(router.view)
    }
}

package com.github.madfoxoo.foxwallet.root.nav

import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.github.madfoxoo.foxwallet.root.nav.home.HomeBuilder
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link NavigationBuilder.NavigationScope}.
 */
@Mockable
class NavigationRouter(
    view: NavigationView,
    interactor: NavigationInteractor,
    component: NavigationBuilder.Component,
    private val homeBuilder: HomeBuilder
) : ViewRouter<NavigationView, NavigationInteractor>(
    view,
    interactor,
    component
) {

    fun attachHome() {
        val router = homeBuilder.build(view)
        attachChild(router)
        view.addView(router.view)
    }
}

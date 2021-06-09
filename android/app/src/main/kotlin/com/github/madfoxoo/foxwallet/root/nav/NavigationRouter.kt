package com.github.madfoxoo.foxwallet.root.nav

import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.github.madfoxoo.foxwallet.root.nav.home.HomeBuilder
import com.github.madfoxoo.foxwallet.root.nav.menu.MenuBuilder
import com.github.madfoxoo.foxwallet.root.nav.payments.PaymentsBuilder
import com.github.madfoxoo.foxwallet.root.nav.statistics.StatisticsBuilder
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link NavigationBuilder.NavigationScope}.
 */
@Mockable
class NavigationRouter(
    view: NavigationView,
    interactor: NavigationInteractor,
    component: NavigationBuilder.Component,
    private val homeBuilder: HomeBuilder,
    private val paymentsBuilder: PaymentsBuilder,
    private val statisticsBuilder: StatisticsBuilder,
    private val menuBuilder: MenuBuilder
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

    fun attachPayments() {
        val router = paymentsBuilder.build(view)
        attachChild(router)
        view.addView(router.view)
    }

    fun attachStatistics() {
        val router = statisticsBuilder.build(view)
        attachChild(router)
        view.addView(router.view)
    }

    fun attachMenu() {
        val router = menuBuilder.build(view)
        attachChild(router)
        view.addView(router.view)
    }
}

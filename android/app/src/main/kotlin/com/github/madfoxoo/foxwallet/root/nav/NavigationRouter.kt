package com.github.madfoxoo.foxwallet.root.nav

import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.github.madfoxoo.foxwallet.root.nav.home.HomeBuilder
import com.github.madfoxoo.foxwallet.root.nav.home.HomeRouter
import com.github.madfoxoo.foxwallet.root.nav.menu.MenuBuilder
import com.github.madfoxoo.foxwallet.root.nav.menu.MenuRouter
import com.github.madfoxoo.foxwallet.root.nav.payments.PaymentsBuilder
import com.github.madfoxoo.foxwallet.root.nav.payments.PaymentsRouter
import com.github.madfoxoo.foxwallet.root.nav.statistics.StatisticsBuilder
import com.github.madfoxoo.foxwallet.root.nav.statistics.StatisticsRouter
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

    private var homeRouter: HomeRouter? = null
    private var paymentsRouter: PaymentsRouter? = null
    private var statisticsRouter: StatisticsRouter? = null
    private var menuRouter: MenuRouter? = null

    fun attachHome() {
        val router = homeBuilder.build(view)
        attachChild(router)
        view.addView(router.view)
        homeRouter = router
    }

    fun detachHome() {
        val router = homeRouter ?: return

        detachChild(router)
        view.removeView(router.view)
        homeRouter = null
    }

    fun attachPayments() {
        val router = paymentsBuilder.build(view)
        attachChild(router)
        view.addView(router.view)
        paymentsRouter = router
    }

    fun detachPayments() {
        val router = paymentsRouter ?: return

        detachChild(router)
        view.removeView(router.view)
        paymentsRouter = null
    }

    fun attachStatistics() {
        val router = statisticsBuilder.build(view)
        attachChild(router)
        view.addView(router.view)
    }

    fun detachStatistics() {
        val router = statisticsRouter ?: return

        detachChild(router)
        view.removeView(router.view)
        statisticsRouter = null
    }

    fun attachMenu() {
        val router = menuBuilder.build(view)
        attachChild(router)
        view.addView(router.view)
    }

    fun detachMenu() {
        val router = menuRouter ?: return

        detachChild(menuRouter)
        view.removeView(router.view)
        menuRouter = null
    }
}

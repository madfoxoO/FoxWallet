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
        homeRouter = attach(homeBuilder.build(view))
    }

    fun detachHome() {
        detach(homeRouter)
        homeRouter = null
    }

    fun attachPayments() {
        paymentsRouter = attach(paymentsBuilder.build(view))
    }

    fun detachPayments() {
        detach(paymentsRouter)
        paymentsRouter = null
    }

    fun attachStatistics() {
        statisticsRouter = attach(statisticsBuilder.build(view))
    }

    fun detachStatistics() {
        detach(statisticsRouter)
        statisticsRouter = null
    }

    fun attachMenu() {
        menuRouter = attach(menuBuilder.build(view))
    }

    fun detachMenu() {
        detach(menuRouter)
        menuRouter = null
    }

    private fun <T : ViewRouter<*, *>> attach(router: T): T {
        attachChild(router)
        view.addView(router.view)
        return router
    }

    private fun detach(router: ViewRouter<*, *>?) {
        if (router != null) {
            detachChild(router)
            view.removeView(router.view)
        }
    }
}

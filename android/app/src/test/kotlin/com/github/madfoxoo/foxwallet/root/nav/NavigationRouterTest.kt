package com.github.madfoxoo.foxwallet.root.nav

import com.github.madfoxoo.foxwallet.root.nav.home.HomeBuilder
import com.github.madfoxoo.foxwallet.root.nav.home.HomeRouter
import com.github.madfoxoo.foxwallet.root.nav.home.HomeView
import com.github.madfoxoo.foxwallet.root.nav.menu.MenuBuilder
import com.github.madfoxoo.foxwallet.root.nav.menu.MenuRouter
import com.github.madfoxoo.foxwallet.root.nav.menu.MenuView
import com.github.madfoxoo.foxwallet.root.nav.payments.PaymentsBuilder
import com.github.madfoxoo.foxwallet.root.nav.payments.PaymentsRouter
import com.github.madfoxoo.foxwallet.root.nav.payments.PaymentsView
import com.github.madfoxoo.foxwallet.root.nav.statistics.StatisticsBuilder
import com.github.madfoxoo.foxwallet.root.nav.statistics.StatisticsRouter
import com.github.madfoxoo.foxwallet.root.nav.statistics.StatisticsView
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.uber.rib.core.RouterHelper
import com.uber.rib.core.ViewRouter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NavigationRouterTest {

    @Mock lateinit var homeBuilder: HomeBuilder
    @Mock lateinit var homeRouter: HomeRouter
    @Mock lateinit var homeView: HomeView

    @Mock lateinit var paymentsBuilder: PaymentsBuilder
    @Mock lateinit var paymentsRouter: PaymentsRouter
    @Mock lateinit var paymentsView: PaymentsView

    @Mock lateinit var statisticsBuilder: StatisticsBuilder
    @Mock lateinit var statisticsRouter: StatisticsRouter
    @Mock lateinit var statisticsView: StatisticsView

    @Mock lateinit var menuBuilder: MenuBuilder
    @Mock lateinit var menuRouter: MenuRouter
    @Mock lateinit var menuView: MenuView

    @Mock lateinit var component: NavigationBuilder.Component
    @Mock lateinit var interactor: NavigationInteractor
    @Mock lateinit var view: NavigationView

    private lateinit var router: NavigationRouter

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        router = NavigationRouter(
            view,
            interactor,
            component,
            homeBuilder,
            paymentsBuilder,
            statisticsBuilder,
            menuBuilder
        )
        RouterHelper.attach(router)
    }

    @Test
    fun attachesHomeRibToNavigationRib() {
        stubHomeRib()

        router.attachHome()

        verifyRibAttached(homeRouter)
    }

    @Test
    fun detachesHomeRibFromNavigationRib() {
        stubHomeRib()

        router.attachHome()
        router.detachHome()

        verifyRibDetached(homeRouter)
    }

    @Test
    fun attachesPaymentsRibToNavigationRib() {
        stubPaymentsRib()

        router.attachPayments()

        verifyRibAttached(paymentsRouter)
    }

    @Test
    fun detachesPaymentsRibFromNavigationRib() {
        stubPaymentsRib()

        router.attachPayments()
        router.detachPayments()

        verifyRibDetached(paymentsRouter)
    }

    @Test
    fun attachesStatisticsRibToNavigationRib() {
        stubStatisticsRib()

        router.attachStatistics()

        verifyRibAttached(statisticsRouter)
    }

    @Test
    fun detachesStatisticsRibFromNavigationRib() {
        stubStatisticsRib()

        router.attachStatistics()
        router.detachStatistics()

        verifyRibDetached(statisticsRouter)
    }

    @Test
    fun attachesMenuRibToNavigationRib() {
        stubMenuRib()

        router.attachMenu()

        verifyRibAttached(menuRouter)
    }

    @Test
    fun detachesMenuRibFromNavigationRib() {
        stubMenuRib()

        router.attachMenu()
        router.detachMenu()

        verifyRibDetached(menuRouter)
    }

    private fun verifyRibAttached(viewRouter: ViewRouter<*, *>) {
        RouterHelper.verifyAttached(viewRouter)
        verify(view).addView(viewRouter.view)
    }

    private fun verifyRibDetached(viewRouter: ViewRouter<*, *>) {
        RouterHelper.verifyDetached(viewRouter)
        verify(view).removeView(viewRouter.view)
    }

    private fun stubHomeRib() {
        whenever(homeBuilder.build(any())).thenReturn(homeRouter)
        whenever(homeRouter.view).thenReturn(homeView)
    }

    private fun stubPaymentsRib() {
        whenever(paymentsBuilder.build(any())).thenReturn(paymentsRouter)
        whenever(paymentsRouter.view).thenReturn(paymentsView)
    }

    private fun stubStatisticsRib() {
        whenever(statisticsBuilder.build(any())).thenReturn(statisticsRouter)
        whenever(statisticsRouter.view).thenReturn(statisticsView)
    }

    private fun stubMenuRib() {
        whenever(menuBuilder.build(any())).thenReturn(menuRouter)
        whenever(menuRouter.view).thenReturn(menuView)
    }
}

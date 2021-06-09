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
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NavigatorRouterTest {

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
    fun attachesHomeRib() {
        stubHomeRib()

        router.attachHome()

        RouterHelper.verifyAttached(homeRouter)
        verify(view).addView(homeView)
    }

    @Test
    fun attachesPaymentsRib() {
        stubPaymentsRib()

        router.attachPayments()

        RouterHelper.verifyAttached(paymentsRouter)
        verify(view).addView(paymentsView)
    }

    @Test
    fun attachesStatisticsRib() {
        stubStatisticsRib()

        router.attachStatistics()

        RouterHelper.verifyAttached(statisticsRouter)
        verify(view).addView(statisticsView)
    }

    @Test
    fun attachesMenuRib() {
        stubMenuRib()

        router.attachMenu()

        RouterHelper.verifyAttached(menuRouter)
        verify(view).addView(menuView)
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

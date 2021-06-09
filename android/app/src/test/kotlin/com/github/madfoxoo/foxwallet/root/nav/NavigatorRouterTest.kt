package com.github.madfoxoo.foxwallet.root.nav

import com.github.madfoxoo.foxwallet.root.nav.home.HomeBuilder
import com.github.madfoxoo.foxwallet.root.nav.home.HomeRouter
import com.github.madfoxoo.foxwallet.root.nav.home.HomeView
import com.github.madfoxoo.foxwallet.root.nav.payments.PaymentsBuilder
import com.github.madfoxoo.foxwallet.root.nav.payments.PaymentsRouter
import com.github.madfoxoo.foxwallet.root.nav.payments.PaymentsView
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

    @Mock lateinit var component: NavigationBuilder.Component
    @Mock lateinit var interactor: NavigationInteractor
    @Mock lateinit var view: NavigationView

    private lateinit var router: NavigationRouter

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        router = NavigationRouter(view, interactor, component, homeBuilder, paymentsBuilder)

        whenever(homeBuilder.build(any())).thenReturn(homeRouter)
        whenever(homeRouter.view).thenReturn(homeView)

        whenever(paymentsBuilder.build(any())).thenReturn(paymentsRouter)
        whenever(paymentsRouter.view).thenReturn(paymentsView)
    }

    @Test
    fun attachesHomeRib() {
        RouterHelper.attach(router)
        router.attachHome()

        RouterHelper.verifyAttached(homeRouter)
        verify(view).addView(homeView)
    }

    @Test
    fun attachesPaymentsRib() {
        RouterHelper.attach(router)
        router.attachPayments()

        RouterHelper.verifyAttached(paymentsRouter)
        verify(view).addView(paymentsView)
    }
}
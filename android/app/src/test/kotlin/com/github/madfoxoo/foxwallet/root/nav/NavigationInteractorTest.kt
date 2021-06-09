package com.github.madfoxoo.foxwallet.root.nav

import com.github.madfoxoo.foxwallet.root.nav.NavigationInteractor.NavigationItem
import com.github.madfoxoo.foxwallet.root.nav.NavigationInteractorStateMatchers.hasSelectedNavigationItem
import com.github.madfoxoo.foxwallet.root.nav.NavigationPresenter.UiEvent
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.uber.rib.core.InteractorHelper
import io.reactivex.observers.assertLastValue
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NavigationInteractorTest {

    @Mock
    lateinit var router: NavigationRouter

    @Mock
    lateinit var presenter: NavigationPresenter

    private lateinit var interactor: NavigationInteractor

    private lateinit var uiEventsSubject: Subject<UiEvent>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        interactor = TestNavigationInteractor.create(presenter, Schedulers.trampoline())

        uiEventsSubject = PublishSubject.create()
        whenever(presenter.observeUiEvents()).thenReturn(uiEventsSubject)
    }

    @Test
    fun attachesHomeRibWhenBecomesActive() {
        InteractorHelper.attach(interactor, presenter, router, null)

        verify(router).attachHome()
    }

    @Test
    fun switchesToPaymentsRibWhenUserSelectsIt() {
        val statesObserver = interactor.observeStates().test()

        InteractorHelper.attach(interactor, presenter, router, null)
        havingReceived(UiEvent.NavigationItemSelected(NavigationItem.PAYMENTS))

        statesObserver.assertLastValue(
            "selected navigation item",
            hasSelectedNavigationItem(NavigationItem.PAYMENTS)
        )
        verify(router).detachHome()
        verify(router).attachPayments()
    }

    @Test
    fun switchesToStatisticsRibWhenUserSelectsIt() {
        val statesObserver = interactor.observeStates().test()

        InteractorHelper.attach(interactor, presenter, router, null)
        havingReceived(
            UiEvent.NavigationItemSelected(NavigationItem.PAYMENTS),
            UiEvent.NavigationItemSelected(NavigationItem.STATISTICS)
        )

        statesObserver.assertLastValue(
            "selected navigation item",
            hasSelectedNavigationItem(NavigationItem.STATISTICS)
        )
        verify(router).detachPayments()
        verify(router).attachStatistics()
    }

    @Test
    fun switchesToMenuRibWhenUserSelectsIt() {
        val statesObserver = interactor.observeStates().test()

        InteractorHelper.attach(interactor, presenter, router, null)
        havingReceived(
            UiEvent.NavigationItemSelected(NavigationItem.STATISTICS),
            UiEvent.NavigationItemSelected(NavigationItem.MENU)
        )

        statesObserver.assertLastValue(
            "selected navigation item",
            hasSelectedNavigationItem(NavigationItem.MENU)
        )
        verify(router).detachStatistics()
        verify(router).attachMenu()
    }

    @Test
    fun switchesToHomeRibWhenUserSelectsIt() {
        val statesObserver = interactor.observeStates().test()

        InteractorHelper.attach(interactor, presenter, router, null)
        havingReceived(
            UiEvent.NavigationItemSelected(NavigationItem.MENU),
            UiEvent.NavigationItemSelected(NavigationItem.HOME)
        )

        statesObserver.assertLastValue(
            "selected navigation item",
            hasSelectedNavigationItem(NavigationItem.HOME)
        )
        verify(router).detachMenu()
        verify(router, times(2)).attachHome()
    }

    private fun havingReceived(vararg uiEvents: UiEvent) {
        for (uiEvent in uiEvents) {
            uiEventsSubject.onNext(uiEvent)
        }
    }
}

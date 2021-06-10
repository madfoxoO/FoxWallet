package com.github.madfoxoo.foxwallet.root.nav

import com.github.madfoxoo.foxwallet.root.nav.NavigationInteractor.NavigationItem
import com.github.madfoxoo.foxwallet.root.nav.NavigationPresenter.UiEvent
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.uber.rib.core.InteractorHelper
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NavigationInteractorTest {

    @Mock lateinit var router: NavigationRouter
    @Mock lateinit var presenter: NavigationPresenter

    private lateinit var interactor: NavigationInteractor

    private lateinit var uiEventsSubject: Subject<UiEvent>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        interactor = TestNavigationInteractor.create(presenter)

        uiEventsSubject = PublishSubject.create()
        whenever(presenter.observeUiEvents()).thenReturn(uiEventsSubject)
    }

    @Test
    fun showsHomeWhenBecomesActive() {
        InteractorHelper.attach(interactor, presenter, router, null)

        verify(presenter).selectNavigationItem(NavigationItem.HOME)
        verify(router).attachHome()
    }

    @Test
    fun ignoresEventWhenUserClicksOnSelectedNavigationItem() {
        havingReceived(UiEvent.NavigationItemSelected(NavigationItem.HOME))

        verify(router, times(1)).attachHome()
        verify(router, never()).detachHome()
    }

    @Test
    fun showsPaymentsWhenUserClicksOnPaymentsNavigationItem() {
        havingReceived(UiEvent.NavigationItemSelected(NavigationItem.PAYMENTS))

        verify(presenter).selectNavigationItem(NavigationItem.PAYMENTS)
        verify(router).detachHome()
        verify(router).attachPayments()
    }

    @Test
    fun showsStatisticsWhenUserClicksOnStatisticsNavigationItem() {
        havingReceived(UiEvent.NavigationItemSelected(NavigationItem.STATISTICS))

        verify(presenter).selectNavigationItem(NavigationItem.STATISTICS)
        verify(router).detachHome()
        verify(router).attachStatistics()
    }

    @Test
    fun showsMenuWhenUserClicksOnMenuNavigationItem() {
        havingReceived(UiEvent.NavigationItemSelected(NavigationItem.MENU))

        verify(presenter).selectNavigationItem(NavigationItem.MENU)
        verify(router).detachHome()
        verify(router).attachMenu()
    }

    private fun havingReceived(vararg uiEvents: UiEvent) {
        InteractorHelper.attach(interactor, presenter, router, null)
        for (uiEvent in uiEvents) {
            uiEventsSubject.onNext(uiEvent)
        }
    }
}

package com.github.madfoxoo.foxwallet.root.nav

import com.github.madfoxoo.foxwallet.R
import com.github.madfoxoo.foxwallet.root.nav.NavigationInteractorStateMatchers.hasSelectedNavigationItemId
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

    private lateinit var uiEventsSubject: Subject<NavigationPresenter.UiEvent>

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
        havingReceived(NavigationPresenter.UiEvent.NavigationItemSelected(R.id.action_payments))

        statesObserver.assertLastValue(
            "selected navigation item id",
            hasSelectedNavigationItemId(R.id.action_payments)
        )
        verify(router).detachHome()
        verify(router).attachPayments()
    }

    private fun havingReceived(event: NavigationPresenter.UiEvent) {
        uiEventsSubject.onNext(event)
    }
}

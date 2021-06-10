package com.github.madfoxoo.foxwallet.core.rib

import android.view.View
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.uber.rib.core.InteractorHelper
import com.uber.rib.core.ViewRouter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MviInteractorTest {

    @Mock lateinit var router: ViewRouter<View, TestMviInteractor>
    @Mock lateinit var presenter: MviPresenter<String, String>

    private lateinit var eventsSubject: Subject<String>
    private lateinit var interactor: TestMviInteractor

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        interactor = spy(TestMviInteractor.create(presenter))

        eventsSubject = PublishSubject.create()
        whenever(presenter.observeUiEvents()).thenReturn(eventsSubject)
    }

    @Test
    fun rendersInitialStateWhenBecomesActive() {
        InteractorHelper.attach(interactor, presenter, router, null)

        verify(presenter).render(INITIAL_STATE)
    }

    @Test
    fun rendersNewStateWhenActionReceived() {
        whenever(interactor.reduce(INITIAL_STATE, ACTION)).thenReturn(NEW_STATE)

        InteractorHelper.attach(interactor, presenter, router, null)
        havingReceived(ACTION)

        verify(presenter).render(NEW_STATE)
        assertThat(interactor.currentState(), equalTo(NEW_STATE))
    }

    @Test
    fun ignoresNewStateWhenItIsTheSameAsLast() {
        InteractorHelper.attach(interactor, presenter, router, null)
        havingReceived(ACTION)

        verify(presenter, times(1)).render(INITIAL_STATE)
    }

    @Test
    fun handlesActionWhenItIsReceived() {
        whenever(interactor.observeInitialActions()).thenReturn(Observable.just(INITIAL_ACTION))
        whenever(interactor.handle(INITIAL_ACTION)).thenReturn(Observable.just(ACTION))
        whenever(interactor.handle(ACTION)).thenReturn(Observable.empty())

        InteractorHelper.attach(interactor, presenter, router, null)
        havingReceived(ACTION)

        verify(interactor).handle(INITIAL_ACTION)
        verify(interactor, times(2)).handle(ACTION)
    }

    private fun havingReceived(action: String) {
        eventsSubject.onNext(action)
    }

    companion object {
        const val INITIAL_STATE = "initial state"
        const val NEW_STATE = "new state"
        const val INITIAL_ACTION = "initial action"
        const val ACTION = "action"
    }
}

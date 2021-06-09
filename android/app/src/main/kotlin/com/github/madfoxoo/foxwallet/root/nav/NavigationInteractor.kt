package com.github.madfoxoo.foxwallet.root.nav

import com.github.madfoxoo.foxwallet.R
import com.github.madfoxoo.foxwallet.core.rib.MviInteractor
import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.github.madfoxoo.foxwallet.dagger.ThreadConfig
import com.github.madfoxoo.foxwallet.root.nav.NavigationPresenter.UiEvent
import com.uber.rib.core.Bundle
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

/**
 * Coordinates Business Logic for [NavigationScope].
 */
@RibInteractor
@Mockable
class NavigationInteractor : MviInteractor<NavigationInteractor.State, NavigationPresenter, NavigationRouter>() {

    @Inject
    override lateinit var presenter: NavigationPresenter

    @Inject
    @ThreadConfig(ThreadConfig.Type.UI)
    override lateinit var uiScheduler: Scheduler

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        router.attachHome()
    }

    override fun reduce(state: State, action: Any): State {
        return when (action) {
            is UiEvent -> reduce(state, action)
            is SideEffect -> reduce(state, action)
            else -> state
        }
    }

    private fun reduce(state: State, uiEvent: UiEvent): State {
        return when (uiEvent) {
            is UiEvent.NavigationItemSelected -> state
        }
    }

    private fun reduce(state: State, sideEffect: SideEffect): State {
        return when (sideEffect) {
            is SideEffect.RibAttached -> state.copy(selectedNavigationItemId = sideEffect.id)
        }
    }

    override fun handle(action: Any): Observable<Any> {
        return when (action) {
            is UiEvent -> handle(action)
            is SideEffect -> handle(action)
            else -> Observable.empty()
        }
    }

    private fun handle(uiEvent: UiEvent): Observable<Any> {
        return when (uiEvent) {
            is UiEvent.NavigationItemSelected -> {
                observeStates().firstElement()
                    .filter { state -> state.selectedNavigationItemId != uiEvent.id }
                    .doOnSuccess {
                        router.detachHome()
                        router.attachPayments()
                    }
                    .flatMapObservable {
                        Observable.just(SideEffect.RibAttached(uiEvent.id))
                    }
            }
        }
    }

    private fun handle(sideEffect: SideEffect): Observable<Any> {
        return when (sideEffect) {
            is SideEffect.RibAttached -> Observable.empty()
        }
    }

    override fun createInitialState(savedInstanceState: Bundle?): State {
        return State(selectedNavigationItemId = R.id.action_home)
    }

    override fun observeInitialActions(): Observable<Any> {
        return Observable.empty()
    }

    data class State(val selectedNavigationItemId: Int)

    private sealed class SideEffect {
        class RibAttached(val id: Int) : SideEffect()
    }
}

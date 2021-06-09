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
class NavigationInteractor :
    MviInteractor<NavigationInteractor.State, NavigationPresenter, NavigationRouter>() {

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
            is SideEffect.OnRibSwitched -> state.copy(selectedNavigationItem = sideEffect.item)
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
                    .filter { state -> state.selectedNavigationItem != uiEvent.item }
                    .doOnSuccess { state ->
                        state.selectedNavigationItem.detach(router)
                        uiEvent.item.attach(router)
                    }
                    .map<Any> { SideEffect.OnRibSwitched(uiEvent.item) }
                    .toObservable()
            }
        }
    }

    private fun handle(sideEffect: SideEffect): Observable<Any> {
        return when (sideEffect) {
            is SideEffect.OnRibSwitched -> Observable.empty()
        }
    }

    override fun createInitialState(savedInstanceState: Bundle?): State {
        return State(selectedNavigationItem = NavigationItem.HOME)
    }

    override fun observeInitialActions(): Observable<Any> {
        return Observable.empty()
    }

    data class State(val selectedNavigationItem: NavigationItem)

    enum class NavigationItem(val id: Int) {
        HOME(R.id.action_home) {
            override fun attach(router: NavigationRouter) {
                router.attachHome()
            }

            override fun detach(router: NavigationRouter) {
                router.detachHome()
            }
        },
        PAYMENTS(R.id.action_payments) {
            override fun attach(router: NavigationRouter) {
                router.attachPayments()
            }

            override fun detach(router: NavigationRouter) {
                router.detachPayments()
            }
        },
        STATISTICS(R.id.action_statistics) {
            override fun attach(router: NavigationRouter) {
                router.attachStatistics()
            }

            override fun detach(router: NavigationRouter) {
                router.detachStatistics()
            }
        },
        MENU(R.id.action_menu) {
            override fun attach(router: NavigationRouter) {
                router.attachMenu()
            }

            override fun detach(router: NavigationRouter) {
                router.detachMenu()
            }
        };

        abstract fun attach(router: NavigationRouter)
        abstract fun detach(router: NavigationRouter)
    }

    private sealed class SideEffect {
        class OnRibSwitched(val item: NavigationItem) : SideEffect()
    }
}

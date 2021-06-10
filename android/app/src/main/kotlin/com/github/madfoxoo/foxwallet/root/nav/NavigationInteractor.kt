package com.github.madfoxoo.foxwallet.root.nav

import com.github.madfoxoo.foxwallet.R
import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.github.madfoxoo.foxwallet.root.nav.NavigationPresenter.UiEvent
import com.uber.autodispose.autoDispose
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [NavigationScope].
 */
@RibInteractor
@Mockable
class NavigationInteractor : Interactor<NavigationPresenter, NavigationRouter>() {

    @Inject
    lateinit var presenter: NavigationPresenter

    private var selectedNavigationItem: NavigationItem? = null

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        presenter.observeUiEvents()
            .startWith(UiEvent.NavigationItemSelected(NavigationItem.HOME))
            .doOnNext { event -> handle(event) }
            .autoDispose(this)
            .subscribe()
    }

    private fun handle(event: UiEvent) {
        when (event) {
            is UiEvent.NavigationItemSelected -> navigateTo(event.item)
        }
    }

    private fun navigateTo(navigationItem: NavigationItem) {
        if (selectedNavigationItem == navigationItem) {
            return
        }

        selectedNavigationItem?.detachFrom(router)
        navigationItem.attachTo(router)
        selectedNavigationItem = navigationItem
        presenter.selectNavigationItem(navigationItem)
    }

    enum class NavigationItem(val id: Int) {
        HOME(R.id.action_home) {
            override fun attachTo(router: NavigationRouter) {
                router.attachHome()
            }

            override fun detachFrom(router: NavigationRouter) {
                router.detachHome()
            }
        },
        PAYMENTS(R.id.action_payments) {
            override fun attachTo(router: NavigationRouter) {
                router.attachPayments()
            }

            override fun detachFrom(router: NavigationRouter) {
                router.detachPayments()
            }
        },
        STATISTICS(R.id.action_statistics) {
            override fun attachTo(router: NavigationRouter) {
                router.attachStatistics()
            }

            override fun detachFrom(router: NavigationRouter) {
                router.detachStatistics()
            }
        },
        MENU(R.id.action_menu) {
            override fun attachTo(router: NavigationRouter) {
                router.attachMenu()
            }

            override fun detachFrom(router: NavigationRouter) {
                router.detachMenu()
            }
        };

        abstract fun attachTo(router: NavigationRouter)
        abstract fun detachFrom(router: NavigationRouter)

        companion object {

            @JvmStatic
            fun valueOf(id: Int): NavigationItem {
                return values().first { it.id == id }
            }
        }
    }
}

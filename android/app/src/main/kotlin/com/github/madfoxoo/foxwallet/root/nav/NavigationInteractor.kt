package com.github.madfoxoo.foxwallet.root.nav

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [NavigationScope].
 *
 * This interactor is responsible for switching between top-level components.
 */
@RibInteractor
class NavigationInteractor :
    Interactor<NavigationPresenter, NavigationRouter>() {

    @Inject
    lateinit var presenter: NavigationPresenter

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
    }
}

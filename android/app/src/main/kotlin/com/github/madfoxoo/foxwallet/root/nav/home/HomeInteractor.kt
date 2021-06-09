package com.github.madfoxoo.foxwallet.root.nav.home

import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [HomeScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class HomeInteractor : Interactor<HomePresenter, HomeRouter>() {

    @Inject
    lateinit var presenter: HomePresenter
}

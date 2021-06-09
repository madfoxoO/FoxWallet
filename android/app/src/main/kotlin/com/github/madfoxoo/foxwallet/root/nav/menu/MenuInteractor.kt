package com.github.madfoxoo.foxwallet.root.nav.menu

import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [MenuScope].
 */
@RibInteractor
class MenuInteractor : Interactor<MenuPresenter, MenuRouter>() {

    @Inject
    lateinit var presenter: MenuPresenter
}

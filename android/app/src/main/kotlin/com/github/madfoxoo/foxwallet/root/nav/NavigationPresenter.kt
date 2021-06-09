package com.github.madfoxoo.foxwallet.root.nav

import com.github.madfoxoo.foxwallet.core.rib.MviPresenter

/**
 * Presenter interface implemented by this RIB's view.
 */
interface NavigationPresenter : MviPresenter<NavigationInteractor.State, NavigationPresenter.UiEvent> {

    sealed class UiEvent {
        class NavigationItemSelected(val id: Int) : UiEvent()
    }
}

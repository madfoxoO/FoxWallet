package com.github.madfoxoo.foxwallet.root.nav

import io.reactivex.Observable

/**
 * Presenter interface implemented by this RIB's view.
 */
interface NavigationPresenter {

    fun observeUiEvents(): Observable<UiEvent>

    fun selectNavigationItem(item: NavigationInteractor.NavigationItem)

    sealed class UiEvent {
        class NavigationItemSelected(val item: NavigationInteractor.NavigationItem) : UiEvent()
    }
}

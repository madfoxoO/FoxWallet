package com.github.madfoxoo.foxwallet.core.rib

import io.reactivex.Observable

interface MviPresenter<S, E> {
    fun observeUiEvents(): Observable<E>
    fun render(state: S)
}

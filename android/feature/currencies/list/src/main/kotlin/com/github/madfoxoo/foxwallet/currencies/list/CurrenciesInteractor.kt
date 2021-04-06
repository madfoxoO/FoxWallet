package com.github.madfoxoo.foxwallet.currencies.list

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [CurrenciesScope].
 */
@RibInteractor
class CurrenciesInteractor : Interactor<CurrenciesPresenter, CurrenciesRouter>() {

    @Inject
    lateinit var presenter: CurrenciesPresenter

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
    }
}

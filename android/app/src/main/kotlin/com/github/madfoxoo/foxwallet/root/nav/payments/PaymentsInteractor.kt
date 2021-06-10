package com.github.madfoxoo.foxwallet.root.nav.payments

import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [PaymentsScope].
 */
@RibInteractor
class PaymentsInteractor : Interactor<PaymentsPresenter, PaymentsRouter>() {

    @Inject
    lateinit var presenter: PaymentsPresenter
}

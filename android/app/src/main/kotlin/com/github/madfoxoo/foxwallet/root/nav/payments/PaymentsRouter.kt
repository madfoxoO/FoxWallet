package com.github.madfoxoo.foxwallet.root.nav.payments

import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link PaymentsBuilder.PaymentsScope}.
 */
@Mockable
class PaymentsRouter(
    view: PaymentsView,
    interactor: PaymentsInteractor,
    component: PaymentsBuilder.Component
) : ViewRouter<PaymentsView, PaymentsInteractor>(
    view,
    interactor,
    component
)

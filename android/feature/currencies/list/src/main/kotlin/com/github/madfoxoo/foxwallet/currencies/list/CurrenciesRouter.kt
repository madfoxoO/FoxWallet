package com.github.madfoxoo.foxwallet.currencies.list

import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link CurrenciesBuilder.CurrenciesScope}.
 */
@Mockable
class CurrenciesRouter(
    view: CurrenciesView,
    interactor: CurrenciesInteractor,
    component: CurrenciesBuilder.Component
) : ViewRouter<CurrenciesView, CurrenciesInteractor>(
    view,
    interactor,
    component
)

package com.github.madfoxoo.foxwallet.root

import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link RootBuilder.RootScope}.
 */
@Mockable
class RootRouter internal constructor(
    view: RootView,
    interactor: RootInteractor,
    component: RootBuilder.Component
) : ViewRouter<RootView, RootInteractor>(
    view,
    interactor,
    component
)

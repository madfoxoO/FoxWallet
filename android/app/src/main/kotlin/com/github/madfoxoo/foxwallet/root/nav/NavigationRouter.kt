package com.github.madfoxoo.foxwallet.root.nav

import android.view.View
import com.github.madfoxoo.foxwallet.core.test.Mockable

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link NavigationBuilder.NavigationScope}.
 */
@Mockable
class NavigationRouter(
    view: NavigationView,
    interactor: NavigationInteractor,
    component: NavigationBuilder.Component
) : ViewRouter<NavigationView, NavigationInteractor>(
    view,
    interactor,
    component
)

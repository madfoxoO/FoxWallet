package com.github.madfoxoo.foxwallet.root.nav.home

import android.view.View
import com.github.madfoxoo.foxwallet.core.test.Mockable

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link HomeBuilder.HomeScope}.
 *
 * This router is responsible for navigation inside home screen.
 */
@Mockable
class HomeRouter(
    view: HomeView,
    interactor: HomeInteractor,
    component: HomeBuilder.Component
) : ViewRouter<HomeView, HomeInteractor>(
    view,
    interactor,
    component
)

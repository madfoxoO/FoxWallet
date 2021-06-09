package com.github.madfoxoo.foxwallet.root.nav.menu

import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link MenuBuilder.MenuScope}.
 */
@Mockable
class MenuRouter(
    view: MenuView,
    interactor: MenuInteractor,
    component: MenuBuilder.Component
) : ViewRouter<MenuView, MenuInteractor>(
    view,
    interactor,
    component
)

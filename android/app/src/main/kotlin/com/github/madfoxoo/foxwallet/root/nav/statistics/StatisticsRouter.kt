package com.github.madfoxoo.foxwallet.root.nav.statistics

import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link StatisticsBuilder.StatisticsScope}.
 */
@Mockable
class StatisticsRouter(
    view: StatisticsView,
    interactor: StatisticsInteractor,
    component: StatisticsBuilder.Component
) : ViewRouter<StatisticsView, StatisticsInteractor>(
    view,
    interactor,
    component
)

package com.github.madfoxoo.foxwallet.root.nav.statistics

import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [StatisticsScope].
 */
@RibInteractor
class StatisticsInteractor : Interactor<StatisticsPresenter, StatisticsRouter>() {

    @Inject
    lateinit var presenter: StatisticsPresenter
}

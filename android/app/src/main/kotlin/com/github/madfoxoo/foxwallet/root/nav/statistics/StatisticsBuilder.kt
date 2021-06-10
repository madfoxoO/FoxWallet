package com.github.madfoxoo.foxwallet.root.nav.statistics

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.madfoxoo.foxwallet.R
import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link StatisticsScope}.
 */
@Mockable
class StatisticsBuilder(dependency: ParentComponent) :
    ViewBuilder<StatisticsView, StatisticsRouter, StatisticsBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [StatisticsRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [StatisticsRouter].
     */
    fun build(parentViewGroup: ViewGroup): StatisticsRouter {
        val view = createView(parentViewGroup)
        val interactor = StatisticsInteractor()
        val component = DaggerStatisticsBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.statisticsRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): StatisticsView {
        return inflater
            .inflate(R.layout.rib_statistics, parentViewGroup, false)
            .let { it as StatisticsView }

    }

    interface ParentComponent

    @dagger.Module
    abstract class Module {

        @StatisticsScope
        @Binds
        internal abstract fun presenter(view: StatisticsView): StatisticsPresenter

        @dagger.Module
        companion object {

            @StatisticsScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: StatisticsView,
                interactor: StatisticsInteractor
            ): StatisticsRouter {
                return StatisticsRouter(view, interactor, component)
            }
        }
    }

    @StatisticsScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<StatisticsInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: StatisticsInteractor): Builder

            @BindsInstance
            fun view(view: StatisticsView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun statisticsRouter(): StatisticsRouter
    }

    @Scope
    @Retention(AnnotationRetention.BINARY)
    internal annotation class StatisticsScope

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    internal annotation class StatisticsInternal
}

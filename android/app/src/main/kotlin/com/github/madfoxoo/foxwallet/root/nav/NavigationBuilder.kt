package com.github.madfoxoo.foxwallet.root.nav

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.madfoxoo.foxwallet.R
import com.github.madfoxoo.foxwallet.root.nav.home.HomeBuilder
import com.github.madfoxoo.foxwallet.root.nav.payments.PaymentsBuilder
import com.github.madfoxoo.foxwallet.root.nav.statistics.StatisticsBuilder
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link NavigationScope}.
 *
 * This scope is responsible for the top-level navigation.
 */
class NavigationBuilder(dependency: ParentComponent) :
    ViewBuilder<NavigationView, NavigationRouter, NavigationBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [NavigationRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [NavigationRouter].
     */
    fun build(parentViewGroup: ViewGroup): NavigationRouter {
        val view = createView(parentViewGroup)
        val interactor = NavigationInteractor()
        val component = DaggerNavigationBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.navigationRouter()
    }

    override fun inflateView(
        inflater: LayoutInflater,
        parentViewGroup: ViewGroup
    ): NavigationView {
        return inflater
            .inflate(R.layout.rib_nav, parentViewGroup, false)
            .let { it as NavigationView }
    }

    interface ParentComponent

    @dagger.Module
    abstract class Module {

        @NavigationScope
        @Binds
        internal abstract fun presenter(view: NavigationView): NavigationPresenter

        @dagger.Module
        companion object {

            @NavigationScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: NavigationView,
                interactor: NavigationInteractor
            ): NavigationRouter {
                return NavigationRouter(
                    view,
                    interactor,
                    component,
                    HomeBuilder(component),
                    PaymentsBuilder(component),
                    StatisticsBuilder(component)
                )
            }
        }
    }

    @NavigationScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component :
        InteractorBaseComponent<NavigationInteractor>,
        HomeBuilder.ParentComponent,
        PaymentsBuilder.ParentComponent,
        StatisticsBuilder.ParentComponent,
        BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: NavigationInteractor): Builder

            @BindsInstance
            fun view(view: NavigationView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun navigationRouter(): NavigationRouter
    }

    @Scope
    @Retention(AnnotationRetention.BINARY)
    internal annotation class NavigationScope

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    internal annotation class NavigationInternal
}

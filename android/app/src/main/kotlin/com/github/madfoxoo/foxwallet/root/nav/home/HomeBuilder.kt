package com.github.madfoxoo.foxwallet.root.nav.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link HomeScope}.
 */
@Mockable
class HomeBuilder(dependency: ParentComponent) :
    ViewBuilder<HomeView, HomeRouter, HomeBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [HomeRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [HomeRouter].
     */
    fun build(parentViewGroup: ViewGroup): HomeRouter {
        val view = createView(parentViewGroup)
        val interactor = HomeInteractor()
        val component = DaggerHomeBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.homeRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): HomeView {
        return HomeView(parentViewGroup.context)
    }

    interface ParentComponent

    @dagger.Module
    abstract class Module {

        @HomeScope
        @Binds
        internal abstract fun presenter(view: HomeView): HomePresenter

        @dagger.Module
        companion object {

            @HomeScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: HomeView,
                interactor: HomeInteractor
            ): HomeRouter {
                return HomeRouter(
                    view,
                    interactor,
                    component
                )
            }
        }
    }

    @HomeScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<HomeInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: HomeInteractor): Builder

            @BindsInstance
            fun view(view: HomeView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun homeRouter(): HomeRouter
    }

    @Scope
    @Retention(AnnotationRetention.BINARY)
    internal annotation class HomeScope

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    internal annotation class HomeInternal
}

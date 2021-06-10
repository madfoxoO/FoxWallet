package com.github.madfoxoo.foxwallet.root.nav.menu

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
 * Builder for the {@link MenuScope}.
 */
@Mockable
class MenuBuilder(dependency: ParentComponent) :
    ViewBuilder<MenuView, MenuRouter, MenuBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [MenuRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [MenuRouter].
     */
    fun build(parentViewGroup: ViewGroup): MenuRouter {
        val view = createView(parentViewGroup)
        val interactor = MenuInteractor()
        val component = DaggerMenuBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.menuRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): MenuView {
        return inflater
            .inflate(R.layout.rib_menu, parentViewGroup, false)
            .let { it as MenuView }
    }

    interface ParentComponent

    @dagger.Module
    abstract class Module {

        @MenuScope
        @Binds
        internal abstract fun presenter(view: MenuView): MenuPresenter

        @dagger.Module
        companion object {

            @MenuScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: MenuView,
                interactor: MenuInteractor
            ): MenuRouter {
                return MenuRouter(view, interactor, component)
            }
        }
    }

    @MenuScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<MenuInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: MenuInteractor): Builder

            @BindsInstance
            fun view(view: MenuView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun menuRouter(): MenuRouter
    }

    @Scope
    @Retention(AnnotationRetention.BINARY)
    internal annotation class MenuScope

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    internal annotation class MenuInternal
}

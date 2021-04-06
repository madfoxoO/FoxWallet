package com.github.madfoxoo.foxwallet.currencies.list

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link CurrenciesScope}.
 */
class CurrenciesBuilder(dependency: ParentComponent) :
    ViewBuilder<CurrenciesView, CurrenciesRouter, CurrenciesBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [CurrenciesRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [CurrenciesRouter].
     */
    fun build(parentViewGroup: ViewGroup): CurrenciesRouter {
        val view = createView(parentViewGroup)
        val interactor = CurrenciesInteractor()
        val component = DaggerCurrenciesBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()

        return component.currenciesRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): CurrenciesView {
        return inflater
            .inflate(R.layout.rib_currencies, parentViewGroup, false)
            .let { it as CurrenciesView }
    }

    interface ParentComponent

    @dagger.Module
    abstract class Module {

        @CurrenciesScope
        @Binds
        internal abstract fun presenter(view: CurrenciesView): CurrenciesPresenter

        @dagger.Module
        companion object {

            @CurrenciesScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: CurrenciesView,
                interactor: CurrenciesInteractor
            ): CurrenciesRouter {
                return CurrenciesRouter(view, interactor, component)
            }
        }
    }

    @CurrenciesScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<CurrenciesInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: CurrenciesInteractor): Builder

            @BindsInstance
            fun view(view: CurrenciesView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun currenciesRouter(): CurrenciesRouter
    }

    @Scope
    @Retention(AnnotationRetention.BINARY)
    internal annotation class CurrenciesScope

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    internal annotation class CurrenciesInternal
}

package com.github.madfoxoo.foxwallet.root.nav.payments

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
 * Builder for the {@link PaymentsScope}.
 */
@Mockable
class PaymentsBuilder(dependency: ParentComponent) :
    ViewBuilder<PaymentsView, PaymentsRouter, PaymentsBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [PaymentsRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [PaymentsRouter].
     */
    fun build(parentViewGroup: ViewGroup): PaymentsRouter {
        val view = createView(parentViewGroup)
        val interactor = PaymentsInteractor()
        val component = DaggerPaymentsBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.paymentsRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): PaymentsView {
        return inflater
            .inflate(R.layout.rib_payments, parentViewGroup, false)
            .let { it as PaymentsView }
    }

    interface ParentComponent

    @dagger.Module
    abstract class Module {

        @PaymentsScope
        @Binds
        internal abstract fun presenter(view: PaymentsView): PaymentsPresenter

        @dagger.Module
        companion object {

            @PaymentsScope
            @Provides
            @JvmStatic
            internal fun router(
                component: Component,
                view: PaymentsView,
                interactor: PaymentsInteractor
            ): PaymentsRouter {
                return PaymentsRouter(view, interactor, component)
            }
        }
    }

    @PaymentsScope
    @dagger.Component(
        modules = [Module::class],
        dependencies = [ParentComponent::class]
    )
    interface Component : InteractorBaseComponent<PaymentsInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: PaymentsInteractor): Builder

            @BindsInstance
            fun view(view: PaymentsView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun paymentsRouter(): PaymentsRouter
    }

    @Scope
    @Retention(AnnotationRetention.BINARY)
    internal annotation class PaymentsScope

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    internal annotation class PaymentsInternal
}

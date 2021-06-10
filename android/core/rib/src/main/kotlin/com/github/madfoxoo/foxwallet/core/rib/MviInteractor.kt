package com.github.madfoxoo.foxwallet.core.rib

import androidx.annotation.VisibleForTesting
import com.uber.autodispose.autoDispose
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.Router
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
abstract class MviInteractor<S : Any, P : MviPresenter<S, *>, R : Router<*>> : Interactor<P, R>() {

    private val actionsSubject = PublishSubject.create<Any>()
    private val statesSubject = BehaviorSubject.create<S>()

    abstract val presenter: P

    abstract val uiScheduler: Scheduler

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun observeStates(): Observable<S> = statesSubject.hide()

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun currentState(): S? = statesSubject.value

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        actionsSubject.withLatestFrom(statesSubject) { action, state -> reduce(state, action) }
            .let { observable ->
                if (statesSubject.hasValue()) {
                    observable.startWith(statesSubject.value)
                        .distinctUntilChanged()
                        .skip(1)
                } else {
                    observable.startWith(createInitialState(savedInstanceState))
                        .distinctUntilChanged()
                }
            }
            .autoDispose(this)
            .subscribe { statesSubject.onNext(it) }

        Observable.merge(actionsSubject, observeInitialActions())
            .flatMap { action -> handle(action) }
            .autoDispose(this)
            .subscribe { actionsSubject.onNext(it) }

        presenter.observeUiEvents()
            .autoDispose(this)
            .subscribe { actionsSubject.onNext(it) }

        statesSubject.observeOn(uiScheduler)
            .autoDispose(this)
            .subscribe { presenter.render(it) }
    }

    protected abstract fun createInitialState(savedInstanceState: Bundle?): S

    protected open fun observeInitialActions(): Observable<out Any> {
        return Observable.empty()
    }

    protected open fun reduce(state: S, action: Any): S {
        return state
    }

    protected open fun handle(action: Any): Observable<out Any> {
        return Observable.empty()
    }
}

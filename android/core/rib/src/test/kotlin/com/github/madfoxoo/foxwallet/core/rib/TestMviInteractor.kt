package com.github.madfoxoo.foxwallet.core.rib

import android.view.View
import com.uber.rib.core.Bundle
import com.uber.rib.core.ViewRouter
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

open class TestMviInteractor : MviInteractor<String, MviPresenter<String, String>, ViewRouter<View, TestMviInteractor>>() {

    override lateinit var presenter: MviPresenter<String, String>

    override lateinit var uiScheduler: Scheduler

    public override fun reduce(state: String, action: Any): String {
        return state
    }

    public override fun handle(action: Any): Observable<Any> {
        return Observable.empty()
    }

    public override fun observeInitialActions(): Observable<Any> {
        return Observable.empty()
    }

    public override fun createInitialState(savedInstanceState: Bundle?): String {
        return savedInstanceState?.getString(KEY_STATE) ?: MviInteractorTest.INITIAL_STATE
    }

    companion object {
        const val KEY_STATE = "state"

        fun create(
            presenter: MviPresenter<String, String>,
            uiScheduler: Scheduler = Schedulers.trampoline()
        ): TestMviInteractor {
            val interactor = TestMviInteractor()
            interactor.presenter = presenter
            interactor.uiScheduler = uiScheduler
            return interactor
        }

        fun createSavedState(state: String): Bundle {
            return Bundle().apply {
                putString(KEY_STATE, state)
            }
        }
    }
}

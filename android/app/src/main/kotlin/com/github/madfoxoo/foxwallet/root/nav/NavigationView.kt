package com.github.madfoxoo.foxwallet.root.nav

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.github.madfoxoo.foxwallet.core.test.Mockable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * Top level view for {@link NavigationBuilder.NavigationScope}.
 */
@Mockable
class NavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CoordinatorLayout(context, attrs, defStyle),
    NavigationPresenter {

    private val uiEventsSubject: Subject<NavigationPresenter.UiEvent> = PublishSubject.create()

    override fun observeUiEvents(): Observable<NavigationPresenter.UiEvent> {
        return uiEventsSubject.hide()
    }

    override fun render(state: NavigationInteractor.State) {
        TODO("not implemented")
    }

}

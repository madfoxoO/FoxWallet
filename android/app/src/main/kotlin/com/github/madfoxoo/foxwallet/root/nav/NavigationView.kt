package com.github.madfoxoo.foxwallet.root.nav

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.github.madfoxoo.foxwallet.R
import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.github.madfoxoo.foxwallet.root.nav.NavigationInteractor.NavigationItem
import com.github.madfoxoo.foxwallet.root.nav.NavigationPresenter.UiEvent
import com.google.android.material.bottomnavigation.BottomNavigationView
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

    private val uiEventsSubject: Subject<UiEvent> = PublishSubject.create()

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onFinishInflate() {
        super.onFinishInflate()
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            val navigationItem = NavigationItem.valueOf(menuItem.itemId)
            uiEventsSubject.onNext(UiEvent.NavigationItemSelected(navigationItem))
            true
        }
    }

    override fun observeUiEvents(): Observable<UiEvent> {
        return uiEventsSubject.hide()
    }

    override fun selectNavigationItem(item: NavigationItem) {
        if (bottomNavigationView.selectedItemId != item.id) {
            bottomNavigationView.selectedItemId = item.id
        }
    }
}

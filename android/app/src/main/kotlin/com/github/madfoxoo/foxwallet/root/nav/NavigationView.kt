package com.github.madfoxoo.foxwallet.root.nav

import android.content.Context
import android.util.AttributeSet
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.github.madfoxoo.foxwallet.core.test.Mockable

/**
 * Top level view for {@link NavigationBuilder.NavigationScope}.
 */
@Mockable
class NavigationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CoordinatorLayout(context, attrs, defStyle),
    NavigationPresenter

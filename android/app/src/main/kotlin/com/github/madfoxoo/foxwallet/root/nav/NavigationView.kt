package com.github.madfoxoo.foxwallet.root.nav

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.github.madfoxoo.foxwallet.R
import com.github.madfoxoo.foxwallet.core.test.Mockable
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

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

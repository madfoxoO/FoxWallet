package com.github.madfoxoo.foxwallet.root.nav.statistics

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.github.madfoxoo.foxwallet.core.test.Mockable

/**
 * Top level view for {@link StatisticsBuilder.StatisticsScope}.
 */
@Mockable
class StatisticsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle),
    StatisticsPresenter

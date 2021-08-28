package com.github.madfoxoo.foxwallet.root.nav.payments

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.github.madfoxoo.foxwallet.core.test.Mockable

/**
 * Top level view for {@link PaymentsBuilder.PaymentsScope}.
 */
@Mockable
class PaymentsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle),
    PaymentsPresenter

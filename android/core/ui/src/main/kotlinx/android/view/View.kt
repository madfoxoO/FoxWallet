package android.view

import android.graphics.Rect
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

fun View.updateVisibility(visible: Boolean) {
    val targetVisibility = if (visible) View.VISIBLE else View.GONE
    if (visibility != targetVisibility) {
        visibility = targetVisibility
    }
}

fun View.updatePadding(
    left: Int = paddingLeft,
    top: Int = paddingTop,
    right: Int = paddingRight,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}

fun View.doOnApplyWindowInsets(func: (View, WindowInsetsCompat, Rect) -> WindowInsetsCompat) {
    val initialPadding = recordInitialPadding(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { v, insets ->
        func(v, insets, initialPadding)
    }
    requestApplyInsetsWhenAttached()
}

fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit
        })
    }
}

private fun recordInitialPadding(view: View): Rect {
    return Rect(
        view.paddingLeft,
        view.paddingTop,
        view.paddingRight,
        view.paddingBottom
    )
}
package com.github.madfoxoo.foxwallet.core.ui.view

import android.graphics.Rect
import android.view.View
import android.view.doOnApplyWindowInsets
import android.view.updatePadding
import androidx.core.view.WindowInsetsCompat

class StatusBarWindowInsetsListener : (View, WindowInsetsCompat, Rect) -> WindowInsetsCompat {

    override fun invoke(view: View, insets: WindowInsetsCompat, padding: Rect): WindowInsetsCompat {
        view.updatePadding(top = padding.top + insets.systemWindowInsetTop)
        return insets
    }

    companion object {
        fun applyTo(view: View) {
            view.doOnApplyWindowInsets(StatusBarWindowInsetsListener())
        }
    }
}

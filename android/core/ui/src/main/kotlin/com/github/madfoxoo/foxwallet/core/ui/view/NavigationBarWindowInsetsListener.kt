package com.github.madfoxoo.foxwallet.core.ui.view

import android.graphics.Rect
import android.view.View
import android.view.doOnApplyWindowInsets
import android.view.updatePadding
import androidx.core.view.WindowInsetsCompat

class NavigationBarWindowInsetsListener : (View, WindowInsetsCompat, Rect) -> WindowInsetsCompat {

    override fun invoke(view: View, insets: WindowInsetsCompat, padding: Rect): WindowInsetsCompat {
        view.updatePadding(bottom = padding.bottom + insets.systemWindowInsetBottom)
        return insets
    }

    companion object {
        fun applyTo(view: View) {
            view.doOnApplyWindowInsets(NavigationBarWindowInsetsListener())
        }
    }
}

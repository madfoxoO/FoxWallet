package com.github.madfoxoo.foxwallet.core.ui.view

import android.content.Context
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.github.madfoxoo.foxwallet.core.ui.R

class ContentOutlineProvider(context: Context) : ViewOutlineProvider() {

    private val cornerRadius = context.resources.getDimension(R.dimen.fw_corner_size_backdrop_front)

    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(
            0,
            0,
            view.width,
            view.height + cornerRadius.toInt(),
            cornerRadius
        )
    }

    companion object {
        fun applyTo(view: View) {
            view.outlineProvider = ContentOutlineProvider(view.context)
            view.clipToOutline = true
        }
    }
}

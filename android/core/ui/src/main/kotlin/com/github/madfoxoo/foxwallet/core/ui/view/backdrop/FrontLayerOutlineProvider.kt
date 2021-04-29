package com.github.madfoxoo.foxwallet.core.ui.view.backdrop

import android.content.res.Resources
import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.github.madfoxoo.foxwallet.core.ui.R

class FrontLayerOutlineProvider(resources: Resources) : ViewOutlineProvider() {

    private val cornerRadius = resources.getDimension(R.dimen.fw_corner_size_m)

    override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(
            0,
            0,
            view.width,
            view.height + cornerRadius.toInt(),
            cornerRadius
        )
    }
}

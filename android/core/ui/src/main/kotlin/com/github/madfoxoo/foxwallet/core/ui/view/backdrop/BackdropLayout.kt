package com.github.madfoxoo.foxwallet.core.ui.view.backdrop

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.*
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.github.madfoxoo.foxwallet.core.ui.R

open class BackdropLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    private var backLayer: ViewGroup? = null

    val requireBackLayer: ViewGroup
        get() = checkNotNull(backLayer)

    private var frontLayer: ViewGroup? = null
        set(value) {
            field = value
            frontLayerBehavior = if (value != null) {
                val lp = value.layoutParams as LayoutParams
                if (lp.behavior !is FrontLayerBehavior) {
                    lp.behavior = FrontLayerBehavior()
                }

                value.outlineProvider = FrontLayerOutlineProvider(resources)
                value.clipToOutline = true

                lp.behavior as FrontLayerBehavior
            } else {
                null
            }
        }

    val requireFrontLayer: ViewGroup
        get() = checkNotNull(frontLayer)

    private var frontLayerBehavior: FrontLayerBehavior? = null

    val requireFrontLayerBehavior: FrontLayerBehavior
        get() = checkNotNull(frontLayerBehavior)

    private var maskLayer: View? = null
        set(value) {
            field = value
            if (value != null) {
                val lp = value.layoutParams as LayoutParams
                if (lp.behavior !is MaskLayerBehavior) {
                    lp.behavior = MaskLayerBehavior()
                }

                val maskBehavior = lp.behavior as MaskLayerBehavior
                if (maskBehavior.anchorId == NO_ID) {
                    value.outlineProvider = FrontLayerOutlineProvider(resources)
                    value.clipToOutline = true
                }
            }
        }

    val requireMaskLayer: View
        get() = checkNotNull(maskLayer)

    fun addBackLayerView(child: View) {
        ensureLayers()
        requireBackLayer.addView(child)
    }

    fun removeBackLayerView(child: View) {
        backLayer?.removeView(child)
    }

    fun addFrontLayerView(child: View) {
        ensureLayers()
        requireFrontLayer.addView(child)
    }

    fun removeFrontLayerView(child: View) {
        frontLayer?.removeView(child)
    }

    fun addOnStateChangeListener(listener: (FrontLayerBehavior.State) -> Unit) {
        ensureLayers()
        requireFrontLayerBehavior.addOnStateChangeListener(listener)
    }

    fun removeOnStateChangeListener(listener: (FrontLayerBehavior.State) -> Unit) {
        frontLayerBehavior?.removeOnStateChangeListener(listener)
    }

    fun collapse(callback: () -> Unit = FrontLayerBehavior.EMPTY_CALLBACK) {
        ensureLayers()

        requireMaskLayer.updateVisibility(true)

        val localFrontLayerBehavior = requireFrontLayerBehavior
        post { localFrontLayerBehavior.setState(FrontLayerBehavior.State.COLLAPSED, callback) }
    }

    fun expand(callback: () -> Unit = FrontLayerBehavior.EMPTY_CALLBACK) {
        ensureLayers()

        requireMaskLayer.updateVisibility(false)

        val localFrontLayerBehavior = requireFrontLayerBehavior
        post { localFrontLayerBehavior.setState(FrontLayerBehavior.State.EXPANDED, callback) }
    }

    override fun onViewAdded(child: View) {
        super.onViewAdded(child)
        when (child.id) {
            R.id.back -> {
                check(child is ViewGroup) {
                    "The back layer view must be a subtype of ViewGroup"
                }
                backLayer = child
            }
            R.id.front -> {
                check(child is ViewGroup) {
                    "The front layer view must be a subtype of ViewGroup"
                }
                frontLayer = child
            }
            R.id.mask -> {
                maskLayer = child
            }
        }
    }

    override fun onViewRemoved(child: View) {
        super.onViewRemoved(child)
        when (child.id) {
            R.id.back -> {
                backLayer = null
            }
            R.id.front -> {
                frontLayer = null
            }
            R.id.mask -> {
                maskLayer = null
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        ensureLayers()
    }

    private fun ensureLayers() {
        ensureBackLayer()
        ensureFrontLayer()
        ensureMaskLayer()
    }

    private fun ensureBackLayer() {
        if (backLayer != null) {
            return
        }

        val fl = FrameLayout(context)
        fl.id = R.id.back
        val attrs = intArrayOf(R.attr.colorPrimary)
        val arr = context.obtainStyledAttributes(attrs)
        try {
            val bgColor = arr.getColor(0, Color.TRANSPARENT)
            fl.setBackgroundColor(bgColor)
        } finally {
            arr.recycle()
        }
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(fl, lp)
    }

    private fun ensureFrontLayer() {
        if (frontLayer != null) {
            return
        }

        val fl = FrameLayout(context)
        fl.id = R.id.front
        val attrs = intArrayOf(R.attr.colorSurface)
        val arr = context.obtainStyledAttributes(attrs)
        try {
            val bgColor = arr.getColor(0, Color.TRANSPARENT)
            fl.setBackgroundColor(bgColor)
        } finally {
            arr.recycle()
        }
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(fl, lp)
    }

    private fun ensureMaskLayer() {
        if (maskLayer != null) {
            return
        }

        val v = View(context)
        v.id = R.id.mask
        v.alpha = 0.38f
        v.visibility = View.GONE
        val attrs = intArrayOf(R.attr.colorSurface)
        val arr = context.obtainStyledAttributes(attrs)
        try {
            val bgColor = arr.getColor(0, Color.TRANSPARENT)
            v.setBackgroundColor(bgColor)
        } finally {
            arr.recycle()
        }
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(v, lp)
    }
}

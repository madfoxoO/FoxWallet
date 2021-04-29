package com.github.madfoxoo.foxwallet.core.ui.view.backdrop

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.github.madfoxoo.foxwallet.core.ui.R

class MaskLayerBehavior : CoordinatorLayout.Behavior<View> {

    val anchorId: Int

    constructor(anchorId: Int = View.NO_ID) : super() {
        this.anchorId = anchorId
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.MaskLayerBehavior_Layout)
        try {
            anchorId = arr.getResourceId(
                R.styleable.MaskLayerBehavior_Layout_behavior_anchorId,
                View.NO_ID
            )
        } finally {
            arr.recycle()
        }
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is ViewGroup && dependency.id == R.id.front
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        dependency as ViewGroup

        var changed = false
        changed = changed or updateChildHeight(child, dependency)
        changed = changed or updateChildPosition(child, dependency)

        return changed
    }

    private fun updateChildHeight(
        child: View,
        dependency: ViewGroup
    ): Boolean {
        val childParams = child.layoutParams
        val anchorView = dependency.findViewById<View>(anchorId)
        val height = if (anchorView != null) {
            dependency.bottom - anchorView.bottom
        } else {
            dependency.bottom - dependency.top
        }
        return if (childParams.height != height) {
            childParams.height = height
            child.layoutParams = childParams
            true
        } else {
            false
        }
    }

    private fun updateChildPosition(
        child: View,
        dependency: ViewGroup
    ): Boolean {
        val anchorView = dependency.findViewById<View>(anchorId)
        val y = anchorView?.y ?: dependency.y
        return if (child.y != y) {
            child.y = y
            true
        } else {
            false
        }
    }
}

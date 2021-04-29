package com.github.madfoxoo.foxwallet.core.ui.view.backdrop

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.github.madfoxoo.foxwallet.core.ui.R
import java.lang.ref.WeakReference

class FrontLayerBehavior : CoordinatorLayout.Behavior<ViewGroup> {

    private val translationAnimator: ValueAnimator = ValueAnimator()
    private val stateChangeListeners: MutableList<(State) -> Unit> = mutableListOf()

    private val expandedAnchorId: Int
    private val collapsedAnchorId: Int

    private var viewRef: WeakReference<ViewGroup>? = null

    private var _state: State = State.EXPANDED
        set(value) {
            if (field != value) {
                field = value
                notifyStateChanged(value)
            }
        }

    var state: State
        get() = _state
        set(value) {
            setState(value, EMPTY_CALLBACK)
        }


    private var expandedY: Float = 0f
    private var collapsedY: Float = 0f

    constructor(expandedAnchorId: Int = View.NO_ID, collapsedAnchorId: Int = View.NO_ID) : super() {
        this.expandedAnchorId = expandedAnchorId
        this.collapsedAnchorId = collapsedAnchorId
        setupTranslationAnimator()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val arr = context.obtainStyledAttributes(attrs, R.styleable.FrontLayerBehavior_Layout)
        try {
            expandedAnchorId = arr.getResourceId(
                R.styleable.FrontLayerBehavior_Layout_behavior_expandedAnchorId,
                View.NO_ID
            )
            collapsedAnchorId = arr.getResourceId(
                R.styleable.FrontLayerBehavior_Layout_behavior_collapsedAnchorId,
                View.NO_ID
            )
        } finally {
            arr.recycle()
        }
        setupTranslationAnimator()
    }

    private fun setupTranslationAnimator() {
        translationAnimator.interpolator = LinearInterpolator()
        translationAnimator.duration = 200
    }

    fun addOnStateChangeListener(listener: (State) -> Unit) {
        stateChangeListeners.add(listener)
    }

    fun removeOnStateChangeListener(listener: (State) -> Unit) {
        stateChangeListeners.remove(listener)
    }

    fun setState(targetState: State, callback: () -> Unit) {
        if (_state == targetState || targetState == State.SETTLING) {
            callback()
            return
        }

        val child = viewRef?.get()
        if (child == null) {
            _state = targetState
            callback()
            return
        }

        settleToStatePendingLayout(child, targetState, callback)
    }

    private fun settleToStatePendingLayout(
        child: ViewGroup,
        targetState: State,
        callback: () -> Unit
    ) {
        val parent = child.parent
        if (parent != null && parent.isLayoutRequested && ViewCompat.isAttachedToWindow(child)) {
            child.post { settleToState(child, targetState, callback) }
        } else {
            settleToState(child, targetState, callback)
        }
    }

    private fun settleToState(
        child: ViewGroup,
        targetState: State,
        callback: () -> Unit
    ) {
        endAnimationIfStarted()

        _state = State.SETTLING

        val start: Float
        val end: Float
        when (targetState) {
            State.EXPANDED -> {
                start = collapsedY
                end = expandedY
            }
            State.COLLAPSED -> {
                start = expandedY
                end = collapsedY
            }
            else -> {
                throw IllegalStateException("Cannot settle to $targetState")
            }
        }
        val animatorUpdateListener = ValueAnimator.AnimatorUpdateListener { animator ->
            child.y = animator.animatedValue as Float
        }
        translationAnimator.setFloatValues(start, end)
        translationAnimator.addUpdateListener(animatorUpdateListener)
        translationAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationEnd(animator: Animator) {
                animator as ValueAnimator

                animator.removeUpdateListener(animatorUpdateListener)
                animator.removeListener(this)
                callback()

                _state = targetState
            }

            override fun onAnimationStart(animator: Animator) = Unit

            override fun onAnimationCancel(animator: Animator) = Unit

            override fun onAnimationRepeat(animator: Animator) = Unit
        })
        translationAnimator.start()
    }

    override fun onAttachedToLayoutParams(params: CoordinatorLayout.LayoutParams) {
        super.onAttachedToLayoutParams(params)
        viewRef = null
    }

    override fun onDetachedFromLayoutParams() {
        super.onDetachedFromLayoutParams()
        viewRef = null
    }

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: ViewGroup,
        layoutDirection: Int
    ): Boolean {
        if (viewRef == null) {
            viewRef = WeakReference(child)
        }
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: ViewGroup,
        dependency: View
    ): Boolean {
        return dependency is ViewGroup && dependency.id == R.id.back
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: ViewGroup,
        dependency: View
    ): Boolean {
        dependency as ViewGroup

        Log.d(FrontLayerBehavior::class.java.simpleName, "onDependentViewChanged: ${System.currentTimeMillis()}")

        endAnimationIfStarted()

        var changed = false
        changed = changed or updateChildHeight(parent, child, dependency)
        changed = changed or updateChildPosition(child, dependency)

        return changed

    }

    private fun endAnimationIfStarted() {
        if (translationAnimator.isStarted) {
            translationAnimator.end()
        }
    }

    private fun updateChildHeight(
        parent: CoordinatorLayout,
        child: ViewGroup,
        dependency: ViewGroup
    ): Boolean {
        val childParams = child.layoutParams as CoordinatorLayout.LayoutParams
        val anchorView = dependency.findViewById<View>(expandedAnchorId)
        val height = if (anchorView != null) {
            parent.bottom - anchorView.bottom
        } else {
            parent.bottom - dependency.top
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
        child: ViewGroup,
        dependency: ViewGroup
    ): Boolean {
        val expandedAnchorView = dependency.findViewById<View>(expandedAnchorId)
        expandedY = if (expandedAnchorView != null) {
            expandedAnchorView.bottom + expandedAnchorView.translationY
        } else {
            dependency.top + dependency.translationY
        }
        val collapsedAnchorView = dependency.findViewById<View>(collapsedAnchorId)
        collapsedY = if (collapsedAnchorView != null) {
            collapsedAnchorView.bottom + collapsedAnchorView.translationY
        } else {
            dependency.bottom - dependency.paddingBottom + dependency.translationY
        }
        val y = when (state) {
            State.EXPANDED -> expandedY
            State.COLLAPSED -> collapsedY
            else -> child.y
        }
        return if (child.y != y) {
            child.y = y
            true
        } else {
            false
        }
    }

    private fun notifyStateChanged(newState: State) {
        for (listener in stateChangeListeners) {
            listener(newState)
        }
    }

    companion object {
        @JvmStatic
        val EMPTY_CALLBACK: () -> Unit = {}
    }

    enum class State {
        EXPANDED,
        COLLAPSED,
        SETTLING
    }
}
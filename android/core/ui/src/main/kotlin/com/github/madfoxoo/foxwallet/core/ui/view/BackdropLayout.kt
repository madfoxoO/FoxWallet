package com.github.madfoxoo.foxwallet.core.ui.view

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Outline
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.github.madfoxoo.foxwallet.core.ui.R
import java.lang.ref.WeakReference

open class BackdropLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    private val backLayoutId: Int = R.id.back
    private val frontLayoutId: Int = R.id.front
    private val maskViewId: Int = R.id.mask

    private val peekHeight: Int =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            48f,
            resources.displayMetrics
        ).toInt()


    protected lateinit var backLayout: ViewGroup
    protected lateinit var frontLayout: ViewGroup
    protected lateinit var maskView: View

    var state: State
        get() = getFrontLayerBehavior().state
        set(value) {
            setState(value, EMPTY_CALLBACK)
        }

    fun setState(state: State, callback: () -> Unit) {
        maskView.updateVisibility(state == State.COLLAPSED)
        getFrontLayerBehavior().setState(state, callback)
    }

    private fun getFrontLayerBehavior(): FrontLayerBehavior {
        val lp = frontLayout.layoutParams as LayoutParams
        return lp.behavior as FrontLayerBehavior
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        backLayout = checkNotNull(findViewById(backLayoutId))
        frontLayout = checkNotNull(findViewById(frontLayoutId))
        maskView = checkNotNull(findViewById(maskViewId))

        backLayout.updatePadding(bottom = peekHeight)

        val frontLayerOutlineProvider = FrontLayoutOutlineProvider(context)
        val frontParams = frontLayout.layoutParams as LayoutParams
        frontParams.behavior = FrontLayerBehavior(backLayoutId)
        frontLayout.outlineProvider = frontLayerOutlineProvider
        frontLayout.clipToOutline = true

        val maskParams = maskView.layoutParams as LayoutParams
        maskParams.behavior = MaskLayoutBehavior(frontLayoutId, isMaskPlacedUnderSubtitle())
        maskView.outlineProvider = frontLayerOutlineProvider
        maskView.clipToOutline = true
    }

    protected open fun isMaskPlacedUnderSubtitle(): Boolean {
        return false
    }

    companion object {
        private const val ANIMATION_DURATION: Long = 300

        @JvmField
        val EMPTY_CALLBACK: () -> Unit = {}
    }

    enum class State {
        EXPANDED,
        COLLAPSED
    }

    private class FrontLayerBehavior(
        private val dependencyId: Int
    ) : CoordinatorLayout.Behavior<ViewGroup>() {

        private val transitionAnimation: ValueAnimator = ValueAnimator()

        private var viewRef: WeakReference<ViewGroup>? = null

        private var _state: State = State.EXPANDED

        var state: State
            get() = _state
            set(value) {
                setState(value, EMPTY_CALLBACK)
            }

        private var expandedY: Float = 0f
        private var collapsedY: Float = 0f

        init {
            transitionAnimation.interpolator = AccelerateDecelerateInterpolator()
            transitionAnimation.duration = ANIMATION_DURATION
        }

        fun setState(targetState: State, callback: () -> Unit) {
            if (_state == targetState) {
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

            _state = targetState

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
            }
            val animatorUpdateListener = ValueAnimator.AnimatorUpdateListener { animator ->
                child.y = animator.animatedValue as Float
            }
            transitionAnimation.setFloatValues(start, end)
            transitionAnimation.addUpdateListener(animatorUpdateListener)
            transitionAnimation.addListener(object : Animator.AnimatorListener {
                override fun onAnimationEnd(animator: Animator) {
                    animator as ValueAnimator

                    animator.removeUpdateListener(animatorUpdateListener)
                    animator.removeListener(this)
                    callback()
                }

                override fun onAnimationStart(animator: Animator) = Unit

                override fun onAnimationCancel(animator: Animator) = Unit

                override fun onAnimationRepeat(animator: Animator) = Unit
            })
            transitionAnimation.start()
        }

        override fun onAttachedToLayoutParams(params: LayoutParams) {
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
            return dependency is ViewGroup &&
                dependency.id == dependencyId
        }

        override fun onDependentViewChanged(
            parent: CoordinatorLayout,
            child: ViewGroup,
            dependency: View
        ): Boolean {
            dependency as ViewGroup

            endAnimationIfStarted()

            var changed = false
            changed = changed or updateChildHeight(parent, child, dependency)
            changed = changed or updateChildPosition(parent, child, dependency)

            return changed
        }

        private fun endAnimationIfStarted() {
            if (transitionAnimation.isStarted) {
                transitionAnimation.end()
            }
        }

        private fun updateChildHeight(
            parent: CoordinatorLayout,
            child: ViewGroup,
            dependency: ViewGroup
        ): Boolean {
            val toolbar = dependency.getChildAt(0)
            val childParams = child.layoutParams
            val height = parent.measuredHeight - parent.paddingTop - toolbar.measuredHeight
            return if (childParams.height != height) {
                childParams.height = height
                child.layoutParams = childParams
                true
            } else {
                false
            }
        }

        private fun updateChildPosition(
            parent: CoordinatorLayout,
            child: ViewGroup,
            dependency: ViewGroup
        ): Boolean {
            val toolbar = dependency.getChildAt(0)
            expandedY = toolbar.y + toolbar.measuredHeight
            val back = dependency.getChildAt(1)
            collapsedY = if (back != null) {
                back.y + back.measuredHeight
            } else {
                dependency.y + dependency.measuredHeight - dependency.paddingBottom
            }

            val y = when (state) {
                State.EXPANDED -> expandedY
                State.COLLAPSED -> collapsedY
            }
            return if (child.y != y) {
                child.y = y
                true
            } else {
                false
            }
        }
    }

    private class MaskLayoutBehavior(
        private val dependencyId: Int,
        private val isUnderSubtitle: Boolean
    ) : CoordinatorLayout.Behavior<View>() {

        override fun layoutDependsOn(
            parent: CoordinatorLayout,
            child: View,
            dependency: View
        ): Boolean {
            return dependency is ViewGroup &&
                dependency.id == dependencyId
        }

        override fun onDependentViewChanged(
            parent: CoordinatorLayout,
            child: View,
            dependency: View
        ): Boolean {
            dependency as ViewGroup

            var changed = false
            changed = changed or updateChildHeight(parent, child, dependency)
            changed = changed or updateChildPosition(parent, child, dependency)

            return changed
        }

        private fun updateChildHeight(
            parent: CoordinatorLayout,
            child: View,
            dependency: ViewGroup
        ): Boolean {
            val childParams = child.layoutParams
            val subtitleHeight = if (isUnderSubtitle) {
                val subtitle = dependency.getChildAt(0)
                subtitle.measuredHeight + dependency.paddingTop
            } else {
                0
            }
            val height = dependency.measuredHeight - subtitleHeight
            return if (childParams.height != height) {
                childParams.height = height
                child.layoutParams = childParams
                true
            } else {
                false
            }
        }

        private fun updateChildPosition(
            parent: CoordinatorLayout,
            child: View,
            dependency: ViewGroup
        ): Boolean {
            val y = if (isUnderSubtitle) {
                val subtitle = dependency.getChildAt(0)
                dependency.y + subtitle.measuredHeight
            } else {
                dependency.y
            }
            return if (child.y != y) {
                child.y = y
                true
            } else {
                false
            }
        }
    }

    private class FrontLayoutOutlineProvider(context: Context) : ViewOutlineProvider() {

        private val cornerRadius =
            context.resources.getDimension(R.dimen.fw_corner_size_backdrop_front)

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
}

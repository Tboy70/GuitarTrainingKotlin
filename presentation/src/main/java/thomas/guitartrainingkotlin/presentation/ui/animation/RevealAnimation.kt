package thomas.guitartrainingkotlin.presentation.ui.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AccelerateInterpolator
import thomas.guitartrainingkotlin.presentation.extension.hide
import thomas.guitartrainingkotlin.presentation.extension.show
import kotlin.math.max

private const val DURATION_ANIMATION: Long = 500

class RevealAnimation(private val view: View, intent: Intent, private val activity: Activity) {

    private var revealX = 0
    private var revealY = 0

    init {
        //when you're android version is at least Lollipop it starts the reveal activity
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
            intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
            intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)
        ) {
            view.hide()
            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0)
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0)

            view.viewTreeObserver.let {
                if (it.isAlive) {
                    it.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            revealActivity(revealX, revealY)
                            view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    })
                }
            }
        } else {
            //if you are below android 5 it just shows the activity
            view.show()
        }
    }

    fun revealActivity(x: Int, y: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            // create the animator for this view (the start radius is zero)
            ViewAnimationUtils.createCircularReveal(
                view,
                x,
                y,
                0f,
                (max(view.width, view.height) * 1.1).toFloat()
            ).apply {
                duration = DURATION_ANIMATION
                interpolator = AccelerateInterpolator()
            }.also {
                // make the view visible and start the animation
                view.show()
            }.start()
        } else {
            activity.finish()
        }
    }

    fun unRevealActivity() {
        ViewAnimationUtils.createCircularReveal(
            view, revealX, revealY, (max(view.width, view.height) * 1.1).toFloat(), 0f
        ).apply {
            duration = DURATION_ANIMATION
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.hide()
                    activity.finish()
                    activity.overridePendingTransition(0, 0)
                }
            })
        }.run {
            start()
        }
    }

    companion object {
        const val EXTRA_CIRCULAR_REVEAL_X = "EXTRA_CIRCULAR_REVEAL_X"
        const val EXTRA_CIRCULAR_REVEAL_Y = "EXTRA_CIRCULAR_REVEAL_Y"
    }
}
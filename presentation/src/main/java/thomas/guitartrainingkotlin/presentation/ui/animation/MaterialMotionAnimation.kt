package thomas.guitartrainingkotlin.presentation.ui.animation

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

private const val DURATION_ANIM_MATERIAL_MOTION: Long = 450
private const val ELEVATION_ANIM_MATERIAL_MOTION = 9f

object MaterialMotionAnimation {

    fun postponeEnterTransition(fragment: Fragment, view: View) {
        fragment.postponeEnterTransition()
        view.doOnPreDraw {
            fragment.startPostponedEnterTransition()
        }
    }

    fun setExitSharedElementCallback(activity: Activity) {
        activity.setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        activity.window.sharedElementsUseOverlay = false
    }

    fun setEnterSharedElementCallback(activity: Activity, view: View, transitionName: String) {
        ViewCompat.setTransitionName(view, transitionName)

        // set up shared element transition
        activity.setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        activity.window.sharedElementEnterTransition = getContentTransform(activity, view)
        activity.window.sharedElementReturnTransition = getContentTransform(activity, view)
    }

    private fun getContentTransform(context: Context, target: View): MaterialContainerTransform {
        return MaterialContainerTransform().apply {
            addTarget(target)
            duration = DURATION_ANIM_MATERIAL_MOTION
            startContainerColor = ContextCompat.getColor(context, android.R.color.white)
            startElevation = ELEVATION_ANIM_MATERIAL_MOTION
            endElevation = ELEVATION_ANIM_MATERIAL_MOTION
        }
    }
}
package thomas.guitartrainingkotlin.presentation.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_user_program.*
import thomas.guitartrainingkotlin.R

@AndroidEntryPoint
class UserProgramActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_program)


        ViewCompat.setTransitionName(user_program_nav_host_fragment, "transitionNameA")

        // Set up shared element transition and disable overlay so views don't show above system bars
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())

        val materialTransform = MaterialContainerTransform().apply {
            addTarget(user_program_nav_host_fragment)
            scrimColor = Color.TRANSPARENT
            duration = 1500
            pathMotion = MaterialArcMotion()
        }

        window.sharedElementEnterTransition = materialTransform
    }
}
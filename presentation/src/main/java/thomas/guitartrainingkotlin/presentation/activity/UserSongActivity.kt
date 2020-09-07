package thomas.guitartrainingkotlin.presentation.activity

import android.content.Context
import android.os.Bundle
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import dagger.hilt.android.AndroidEntryPoint
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.utils.ConstValues

@AndroidEntryPoint
class UserSongActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        intent.extras?.let { bundle ->
            if (bundle.containsKey(ConstValues.ID_SONG)) {
                bundle.getString(ConstValues.ID_SONG)?.let {
                    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
                    ViewCompat.setTransitionName(findViewById(android.R.id.content), it)

                    // set up shared element transition
                    setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
                    window.sharedElementEnterTransition = getContentTransform(this)
                    window.sharedElementReturnTransition = getContentTransform(this)
                }
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_song)
    }

    private fun getContentTransform(context: Context): MaterialContainerTransform {
        return MaterialContainerTransform().apply {
            addTarget(android.R.id.content)
            duration = 450
            startContainerColor = ContextCompat.getColor(context, android.R.color.white)
            startElevation = 9f
            endElevation = 9f
        }
    }
}
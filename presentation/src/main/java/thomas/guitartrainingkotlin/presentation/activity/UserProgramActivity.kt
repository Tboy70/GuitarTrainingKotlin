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
import thomas.guitartrainingkotlin.presentation.ui.animation.MaterialMotionAnimation
import thomas.guitartrainingkotlin.presentation.utils.ConstValues

@AndroidEntryPoint
class UserProgramActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        intent.extras?.let { bundle ->
            if (bundle.containsKey(ConstValues.ID_PROGRAM)) {
                bundle.getString(ConstValues.ID_PROGRAM)?.let {
                    MaterialMotionAnimation.setEnterSharedElementCallback(
                        this,
                        findViewById(android.R.id.content),
                        it
                    )
                }
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_program)
    }
}
package thomas.guitartrainingkotlin.presentation.activity

import android.os.Bundle
import android.view.Window
import dagger.hilt.android.AndroidEntryPoint
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.ui.animation.MaterialMotionAnimation
import thomas.guitartrainingkotlin.presentation.utils.ConstValues

@AndroidEntryPoint
class UserSongActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        intent.extras?.let { bundle ->
            if (bundle.containsKey(ConstValues.ID_SONG)) {
                bundle.getString(ConstValues.ID_SONG)?.let {
                    MaterialMotionAnimation.setEnterSharedElementCallback(
                        this,
                        findViewById(android.R.id.content),
                        it
                    )
                }
            }
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_song)
    }
}
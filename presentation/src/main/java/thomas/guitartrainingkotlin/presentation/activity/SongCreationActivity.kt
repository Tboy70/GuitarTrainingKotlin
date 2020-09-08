package thomas.guitartrainingkotlin.presentation.activity

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_program_creation.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.ui.animation.RevealAnimation

@AndroidEntryPoint
class SongCreationActivity : BaseActivity() {

    private var revealAnimation: RevealAnimation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_creation)

        revealAnimation =
            RevealAnimation(
                root_layout,
                intent,
                this
            )
    }

    override fun onBackPressed() {
        revealAnimation?.unRevealActivity()
    }
}
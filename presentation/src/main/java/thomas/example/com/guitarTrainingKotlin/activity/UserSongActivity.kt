package thomas.example.com.guitarTrainingKotlin.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user_song.*
import thomas.example.com.guitarTrainingKotlin.R

class UserSongActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_song)
    }

    fun setToolbar(toolbarName: String) {
        activity_user_song_toolbar.title = toolbarName
    }
}
package thomas.guitartrainingkotlin.presentation.activity

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import thomas.guitartrainingkotlin.R

@AndroidEntryPoint
class GameActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }
}
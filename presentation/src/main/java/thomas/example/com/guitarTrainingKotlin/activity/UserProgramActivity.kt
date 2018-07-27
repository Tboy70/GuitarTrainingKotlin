package thomas.example.com.guitarTrainingKotlin.activity

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_user_program.*
import thomas.example.com.guitarTrainingKotlin.R

class UserProgramActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_program)
    }

    fun setToolbar(toolbarName: String) {
        activity_user_program_toolbar.title = toolbarName
    }
}
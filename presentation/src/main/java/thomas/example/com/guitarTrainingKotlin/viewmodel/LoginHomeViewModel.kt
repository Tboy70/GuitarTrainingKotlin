package thomas.example.com.guitarTrainingKotlin.viewmodel

import android.arch.lifecycle.ViewModel
import android.util.Log
import javax.inject.Inject

class LoginHomeViewModel @Inject constructor() : ViewModel() {

    fun connectUser(username: String, password: String) {
        Log.e("TEST", "OKTM")
    }
}
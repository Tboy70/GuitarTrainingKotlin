package thomas.guitartrainingkotlin.presentation.viewmodel.shared

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import javax.inject.Inject

class ProgramSharedViewModel @ViewModelInject constructor() : ViewModel() {

    val backPressedLiveEvent = SingleLiveEvent<Boolean>()

    fun onBackPressed() {
        backPressedLiveEvent.postValue(true)
    }
}

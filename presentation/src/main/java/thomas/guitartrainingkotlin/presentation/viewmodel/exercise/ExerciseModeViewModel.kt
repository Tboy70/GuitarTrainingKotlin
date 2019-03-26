package thomas.guitartrainingkotlin.presentation.viewmodel.exercise

import androidx.lifecycle.ViewModel
import thomas.guitartrainingkotlin.presentation.fragment.exercise.ExerciseModeFragment
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import java.util.*
import javax.inject.Inject

class ExerciseModeViewModel @Inject constructor() : ViewModel() {

    val finishRandomLiveEvent: SingleLiveEvent<Int> = SingleLiveEvent()

    fun getRandomValue() {
        val scaleNoteRandom = Random()
        finishRandomLiveEvent.postValue(scaleNoteRandom.nextInt(ExerciseModeFragment.NB_MODES))
    }
}
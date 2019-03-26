package thomas.guitartrainingkotlin.presentation.viewmodel.exercise

import androidx.lifecycle.ViewModel
import thomas.guitartrainingkotlin.presentation.fragment.exercise.ExerciseScaleFragment
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import java.util.*
import javax.inject.Inject

class ExerciseScaleViewModel @Inject constructor() : ViewModel() {

    val finishRandomLiveEvent = SingleLiveEvent<Pair<Int, Int>>()

    fun getRandomValue() {
        val scaleNoteRandom = Random()
        val scaleToneRandom = Random()
        finishRandomLiveEvent.postValue(Pair(
            scaleNoteRandom.nextInt(ExerciseScaleFragment.NB_NOTES),
            scaleToneRandom.nextInt(ExerciseScaleFragment.NB_SCALES)
        ))
    }
}
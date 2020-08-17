package thomas.guitartrainingkotlin.presentation.viewmodel.exercise

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import java.util.*

class ExerciseScaleViewModel @ViewModelInject constructor() : ViewModel() {

    val finishRandomLiveEvent = SingleLiveEvent<Pair<Int, Int>>()

    fun getRandomValue() {
        val scaleNoteRandom = Random()
        val scaleToneRandom = Random()
        finishRandomLiveEvent.postValue(
            Pair(
                scaleNoteRandom.nextInt(ConstValues.NB_NOTES),
                scaleToneRandom.nextInt(ConstValues.NB_SCALES)
            )
        )
    }
}
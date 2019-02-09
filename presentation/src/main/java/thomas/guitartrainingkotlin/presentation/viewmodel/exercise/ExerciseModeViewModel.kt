package thomas.guitartrainingkotlin.presentation.viewmodel.exercise

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.guitartrainingkotlin.presentation.fragment.exercise.ExerciseModeFragment
import java.util.*
import javax.inject.Inject

class ExerciseModeViewModel @Inject constructor() : ViewModel() {
    val finishRandom: MutableLiveData<Boolean> = MutableLiveData()

    var modeValue: Int = 0

    fun getRandomValue() {
        val scaleNoteRandom = Random()
        modeValue = scaleNoteRandom.nextInt(ExerciseModeFragment.NB_MODES)

        finishRandom.postValue(true)
    }
}
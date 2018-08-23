package thomas.example.com.guitarTrainingKotlin.viewmodel.exercise

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.fragment.exercise.ExerciseModeFragment
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
package thomas.example.com.guitarTrainingKotlin.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.fragment.exercise.ExerciseScaleFragment
import java.util.*
import javax.inject.Inject

class ExerciseScaleViewModel @Inject constructor() : ViewModel() {

    val finishRandom: MutableLiveData<Boolean> = MutableLiveData()

    var scaleNoteValue: Int = 0
    var scaleToneValue: Int = 0

    fun getRandomValue() {
        val scaleNoteRandom = Random()
        scaleNoteValue = scaleNoteRandom.nextInt(ExerciseScaleFragment.NB_NOTES)

        val scaleToneRandom = Random()
        scaleToneValue = scaleToneRandom.nextInt(ExerciseScaleFragment.NB_MODES)

        finishRandom.postValue(true)
    }


}
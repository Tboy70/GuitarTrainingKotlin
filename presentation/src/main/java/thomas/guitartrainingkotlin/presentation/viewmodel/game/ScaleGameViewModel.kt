package thomas.guitartrainingkotlin.presentation.viewmodel.game

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import thomas.guitartrainingkotlin.presentation.utils.GameUtils
import thomas.guitartrainingkotlin.presentation.utils.GameUtils2
import thomas.guitartrainingkotlin.presentation.view.state.game.ScaleGameViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.AndroidStateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import kotlin.random.Random

class ScaleGameViewModel @ViewModelInject constructor(
    application: Application
) : AndroidStateViewModel<ScaleGameViewState>(application) {

    override val currentViewState = ScaleGameViewState()

    val answerCheckedLiveEvent = SingleLiveEvent<List<Boolean>>()
    val gameModeLiveEvent = SingleLiveEvent<Triple<Int, Int, Int>>()
    val correctScaleLiveEvent = SingleLiveEvent<Pair<List<String>, String>>()
    val generatedRandomScaleLiveEvent = SingleLiveEvent<Pair<List<String>, String>>()
    val answerCheckedWithUserChoiceLiveEvent = SingleLiveEvent<Pair<List<Boolean>, Boolean>>()

    init {
        getScaleGameMode()
    }

    fun getScaleGameMode() {

//        val startNote = Random.nextInt(ConstValues.NB_NOTES)
        val startNoteIndex = 2
        val scaleIndex = 5

        val test = GameUtils2.computeCorrectScale(getApplication(), startNoteIndex, scaleIndex)
        Log.e("TEST", "result : " + test)

        gameModeLiveEvent.postValue(
            Triple(
                startNoteIndex,
                scaleIndex,
                Random.nextInt(0, 1)
            )
        )
    }

    fun checkAnswers(answersList: List<String>, scale: String, referenceNote: String) {
        answerCheckedLiveEvent.postValue(
            GameUtils.checkScaleGameAnswer(
                answersList,
                scale,
                referenceNote,
                getApplication()
            )
        )
    }

    fun checkAnswers(
        answersList: List<String>,
        scale: String,
        note: String,
        isCorrect: Boolean
    ) {
        answerCheckedWithUserChoiceLiveEvent.postValue(
            Pair(
                GameUtils.checkScaleGameAnswer(
                    answersList,
                    scale,
                    note,
                    getApplication()
                ), isCorrect
            )
        )
    }

    fun generateCorrectScale(referenceNote: String) {
        Log.e("TEST", "Besoin d'une gamme correcte --> Jeu Find Scale")
        correctScaleLiveEvent.postValue(
            GameUtils.generateCorrectScale(referenceNote, null, getApplication())
        )
    }

    fun generateRandomScale(referenceNote: String, scale: String) {
        val correctOrIncorrectScale = Random.nextInt(2)
        generatedRandomScaleLiveEvent.postValue(
            if (correctOrIncorrectScale == 0) {
                Log.e("TEST", "Besoin d'une gamme correcte --> Jeu Is Scale Correct ?")
                GameUtils.generateCorrectScale(referenceNote, scale, getApplication())
            } else {
                Log.e("TEST", "Besoin d'une gamme INcorrecte --> Jeu Is Scale Correct ?")
                GameUtils.generateIncorrectScale(referenceNote, scale, getApplication())
            }
        )
    }

    companion object {
        const val NB_SCALE_GAMES = 3
        const val SCALE_GAME_FIND_NOTES = 0
        const val SCALE_GAME_IS_CORRECT_SCALE = 1
        const val SCALE_GAME_FIND_SCALE = 2
    }
}
package thomas.guitartrainingkotlin.presentation.viewmodel.game

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.GameUtils
import thomas.guitartrainingkotlin.presentation.view.state.game.ScaleGameViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.AndroidStateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import java.util.*

class ScaleGameViewModel @ViewModelInject constructor(
    application: Application
) : AndroidStateViewModel<ScaleGameViewState>(application) {

    override val currentViewState = ScaleGameViewState()

    val answerCheckedLiveEvent = SingleLiveEvent<List<Boolean>>()
    val gameRandomizedLiveEvent = SingleLiveEvent<Triple<Int, Int, Int>>()
    val correctScaleLiveEvent = SingleLiveEvent<Pair<List<String>, String>>()
    val generatedRandomScaleLiveEvent = SingleLiveEvent<Pair<List<String>, String>>()
    val answerCheckedWithUserChoiceLiveEvent = SingleLiveEvent<Pair<List<Boolean>, Boolean>>()

    init {
        getRandomGame()
    }

    fun getRandomGame() {
        gameRandomizedLiveEvent.postValue(
            Triple(
                Random().nextInt(ConstValues.NB_NOTES),
                Random().nextInt(ConstValues.NB_SCALES),
                Random().nextInt(ConstValues.SCALE_GAME_MODE)
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
        correctScaleLiveEvent.postValue(
            GameUtils.generateCorrectScale(referenceNote, null, getApplication())
        )
    }

    fun generateRandomScale(referenceNote: String, scale: String) {
        val correctOrIncorrectScale = Random().nextInt(2)
        generatedRandomScaleLiveEvent.postValue(
            if (correctOrIncorrectScale == 0) {
                Log.e("TEST", "On génère une gamme correcte")
                GameUtils.generateCorrectScale(referenceNote, scale, getApplication())
            } else {
                Log.e("TEST", "On génère une gamme INcorrecte")
                GameUtils.generateIncorrectScale(referenceNote, scale, getApplication())
            }
        )
    }
}
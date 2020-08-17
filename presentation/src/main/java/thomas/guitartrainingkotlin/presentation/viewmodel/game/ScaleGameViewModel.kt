package thomas.guitartrainingkotlin.presentation.viewmodel.game

import android.app.Application
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

    val randomScaleLiveEvent = SingleLiveEvent<Pair<List<String>, String>>()
    val answerCheckedLiveEvent = SingleLiveEvent<List<Boolean>>()
    val answerCheckedLiveEvent2 = SingleLiveEvent<Pair<List<Boolean>, Boolean>>()
    val finishRandomLiveEvent = SingleLiveEvent<Triple<Int, Int, Int>>()
    val correctScaleLiveEvent = SingleLiveEvent<Pair<List<String>, String>>()

    init {
        getRandomValue()
    }

    fun getRandomValue() {
        finishRandomLiveEvent.postValue(
            Triple(
                Random().nextInt(ConstValues.NB_NOTES),
                Random().nextInt(ConstValues.NB_SCALES),
                Random().nextInt(ConstValues.SCALE_GAME_MODE)
            )
        )
    }

    fun checkAnswers(answersList: List<String>, givenScale: String, givenNote: String) {
        answerCheckedLiveEvent.postValue(
            GameUtils.checkScaleGameAnswer(
                answersList,
                givenScale,
                givenNote,
                getApplication()
            )
        )
    }

    fun checkAnswers(
        answersList: List<String>,
        givenScale: String,
        givenNote: String,
        isCorrect: Boolean
    ) {
        answerCheckedLiveEvent2.postValue(
            Pair(
                GameUtils.checkScaleGameAnswer(
                    answersList,
                    givenScale,
                    givenNote,
                    getApplication()
                ), isCorrect
            )
        )
    }

    fun generateCorrectScale(givenNote: String) {
        correctScaleLiveEvent.postValue(
            GameUtils.generateCorrectRandomScale(givenNote, getApplication())
        )
    }

    fun generateRandomScale(givenNote: String) {
        val correctOrIncorrectScale = Random().nextInt(2)
        randomScaleLiveEvent.postValue(
            if (correctOrIncorrectScale == 0)
                GameUtils.generateCorrectRandomScale(givenNote, getApplication())
            else
                GameUtils.generateIncorrectRandomScale(givenNote, getApplication())
        )
    }
}
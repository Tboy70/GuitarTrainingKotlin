package thomas.guitartrainingkotlin.presentation.viewmodel.game

import android.app.Application
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.GameUtils
import thomas.guitartrainingkotlin.presentation.view.state.game.ScaleGameViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.AndroidStateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import java.util.*
import javax.inject.Inject

class ScaleGameViewModel @Inject constructor(
    application: Application
) : AndroidStateViewModel<ScaleGameViewState>(application) {

    override val currentViewState = ScaleGameViewState()

    val answerCheckedLiveEvent = SingleLiveEvent<List<Boolean>>()
    val finishRandomLiveEvent = SingleLiveEvent<Triple<Int, Int, Int>>()
    val correctScaleLiveEvent = SingleLiveEvent<Pair<List<String>, String>>()
    val randomScaleLiveEvent = SingleLiveEvent<List<String>>()

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

    fun checkAnswers(
        answersList: List<String>,
        givenScale: String,
        givenNote: String
    ) {

        answerCheckedLiveEvent.postValue(
            GameUtils.checkScaleGameAnswer(
                answersList,
                givenScale,
                givenNote,
                getApplication()
            )
        )
    }

    fun generateCorrectScale(givenNote: String) {
        correctScaleLiveEvent.postValue(
            GameUtils.generateCorrectScale(givenNote, getApplication())
        )
    }

    fun generateRandomScale(givenNote: String) {
        randomScaleLiveEvent.postValue(
            GameUtils.generateRandomScale(givenNote, getApplication())
        )
    }

}
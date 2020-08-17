package thomas.guitartrainingkotlin.presentation.viewmodel.game

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.GameUtils
import thomas.guitartrainingkotlin.presentation.view.state.game.IntervalGameViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.AndroidStateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import java.util.*

class IntervalGameViewModel @ViewModelInject constructor(
    application: Application
) : AndroidStateViewModel<IntervalGameViewState>(application) {

    override val currentViewState = IntervalGameViewState()

    val answerCheckedLiveEvent = SingleLiveEvent<Boolean>()
    val finishRandomLiveEvent = SingleLiveEvent<Triple<Int, Int, Int>>()

    init {
        getRandomValue()
    }

    fun getRandomValue() {
        finishRandomLiveEvent.postValue(
            Triple(
                Random().nextInt(ConstValues.NB_NOTES),
                Random().nextInt(ConstValues.NB_INTERVAL),
                Random().nextInt(ConstValues.INTERVAL_GAME_MODE)
            )
        )
    }

    fun checkAnswer(givenNote: String, givenInterval: String, gameMode: Int, userAnswer: String) {
        answerCheckedLiveEvent.postValue(
            GameUtils.checkIntervalGameAnswer(
                givenNote,
                givenInterval,
                userAnswer,
                gameMode,
                getApplication()
            )
        )
    }
}
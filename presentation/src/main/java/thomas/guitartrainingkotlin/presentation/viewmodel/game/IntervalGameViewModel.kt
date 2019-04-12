package thomas.guitartrainingkotlin.presentation.viewmodel.game

import android.app.Application
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.GameUtils
import thomas.guitartrainingkotlin.presentation.view.state.game.IntervalGameViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.AndroidStateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import java.util.*
import javax.inject.Inject

class IntervalGameViewModel @Inject constructor(
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

    fun checkAnswer(randomNote: String, randomInterval: String, gameMode: Int, answer: String) {
        answerCheckedLiveEvent.postValue(
            GameUtils.checkAnswer(
                randomNote,
                randomInterval,
                answer,
                gameMode,
                getApplication()
            )
        )
    }
}
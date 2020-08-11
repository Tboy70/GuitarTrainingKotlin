package thomas.guitartrainingkotlin.presentation.viewmodel.game

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.GameUtils
import thomas.guitartrainingkotlin.presentation.view.state.game.ReversedIntervalGameViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.AndroidStateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import java.util.*
import javax.inject.Inject

class ReversedIntervalGameViewModel @ViewModelInject constructor(
    application: Application
) : AndroidStateViewModel<ReversedIntervalGameViewState>(application) {

    override val currentViewState = ReversedIntervalGameViewState()

    val answerCheckedLiveEvent = SingleLiveEvent<Boolean>()
    val finishRandomLiveEvent = SingleLiveEvent<Int>()

    init {
        getRandomValue()
    }

    fun getRandomValue() {
        finishRandomLiveEvent.postValue(Random().nextInt(ConstValues.NB_INTERVAL))
    }

    fun checkAnswer(givenInterval: String, answer: String) {
        answerCheckedLiveEvent.postValue(
            GameUtils.checkReversedIntervalGameAnswer(
                givenInterval,
                answer,
                getApplication()
            )
        )
    }
}
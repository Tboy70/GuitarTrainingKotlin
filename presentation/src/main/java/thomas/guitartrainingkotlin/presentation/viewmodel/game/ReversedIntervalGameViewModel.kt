package thomas.guitartrainingkotlin.presentation.viewmodel.game

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.GameUtils
import thomas.guitartrainingkotlin.presentation.view.state.game.ReversedIntervalGameViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.AndroidStateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import kotlin.random.Random

class ReversedIntervalGameViewModel @ViewModelInject constructor(
    application: Application
) : AndroidStateViewModel<ReversedIntervalGameViewState>(application) {

    override val currentViewState = ReversedIntervalGameViewState()

    val finishRandomLiveEvent = SingleLiveEvent<Pair<String, String>>()

    init {
        getRandomValue()
    }

    fun getRandomValue() {
        val randomInterval = Random.nextInt(ConstValues.NB_INTERVAL)

        finishRandomLiveEvent.postValue(
            GameUtils.computeReversedInterval(getApplication(), randomInterval)
        )
    }

    fun computeFalseAnswers(correctAnswer: String): MutableList<String> {
        val falseAnswers = mutableListOf<String>()
        for (i in 0 until 3) {
            var interval = Random.nextInt(ConstValues.NB_INTERVAL)
            var newAnswer = GameUtils.computeFalseAnswers(getApplication(), interval)
            while (newAnswer == correctAnswer || falseAnswers.contains(newAnswer)) {
                interval = Random.nextInt(ConstValues.NB_INTERVAL)
                newAnswer = GameUtils.computeFalseAnswers(getApplication(), interval)
            }

            falseAnswers.add(newAnswer)
        }

        return falseAnswers
    }
}
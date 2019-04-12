package thomas.guitartrainingkotlin.presentation.viewmodel.game

import android.app.Application
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

    val finishRandomLiveEvent = SingleLiveEvent<Pair<Int, Int>>()

    init {
        getRandomValue()
    }

    private fun getRandomValue() {
        val noteValue = Random()
        val intervalValue = Random()
        finishRandomLiveEvent.postValue(
            Pair(
                noteValue.nextInt(12),  // TODO : Export
                intervalValue.nextInt(16)
            )
        )
    }

    fun checkAnswer(randomNote: String, randomInterval: String, answer: String) {
        GameUtils.checkAnswer(randomNote, randomInterval, answer, getApplication())
    }
}
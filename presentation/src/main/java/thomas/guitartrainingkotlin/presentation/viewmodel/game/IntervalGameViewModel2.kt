package thomas.guitartrainingkotlin.presentation.viewmodel.game

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import thomas.guitartrainingkotlin.presentation.view.state.game.IntervalGameViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.AndroidStateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import kotlin.random.Random


class IntervalGameViewModel2 @ViewModelInject constructor(
    application: Application
) : AndroidStateViewModel<IntervalGameViewState>(application) {

    override val currentViewState = IntervalGameViewState()

    val randomizedGameLiveEvent = SingleLiveEvent<Int>()

    init {
        getRandomQuestion()
    }

    private fun getRandomQuestion() {
        randomizedGameLiveEvent.postValue(Random.nextInt(0, 2))
    }

    companion object {
        const val GAME_FIND_NOTE_GIVEN_INTERVAL = 0
        const val GAME_FIND_INTERVAL_GIVEN_NOTE = 1
    }
}
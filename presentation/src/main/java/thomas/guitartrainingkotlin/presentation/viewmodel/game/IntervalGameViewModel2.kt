package thomas.guitartrainingkotlin.presentation.viewmodel.game

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.GameUtils
import thomas.guitartrainingkotlin.presentation.view.state.game.IntervalGameViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.AndroidStateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import kotlin.random.Random

class IntervalGameViewModel2 @ViewModelInject constructor(
    application: Application
) : AndroidStateViewModel<IntervalGameViewState>(application) {

    override val currentViewState = IntervalGameViewState()

    val randomizedGameLiveEvent = SingleLiveEvent<Pair<Int, Triple<Int, Int, String>>>()

    init {
        getRandomValues()
    }

    private fun getRandomValues() {
        val gameMode = Random.nextInt(0, 3)
        if (gameMode == GAME_FIND_NOTE_GIVEN_INTERVAL || gameMode == GAME_FIND_NOTE_GIVEN_INTERVAL_REVERSED) {
            val startNote = Random.nextInt(ConstValues.NB_NOTES)
            val interval = Random.nextInt(ConstValues.NB_INTERVAL)

            val correctAnswer = computeAnswers(gameMode, startNote, interval)

            randomizedGameLiveEvent.postValue(
                Pair(
                    gameMode,
                    Triple(
                        startNote,
                        interval,
                        correctAnswer
                    )
                )
            )


        } else {    // randomGame == GAME_FIND_INTERVAL_GIVEN_NOTES
            val startNote = Random.nextInt(ConstValues.NB_NOTES)
            val endNote = Random.nextInt(ConstValues.NB_NOTES)
            val interval = Random.nextInt(ConstValues.NB_INTERVAL)
            val correctAnswer = computeAnswers(gameMode, startNote, interval)

            randomizedGameLiveEvent.postValue(
                Pair(
                    gameMode,
                    Triple(
                        startNote,
                        interval,
                        correctAnswer
                    )
                )
            )
        }
    }

    private fun computeAnswers(gameMode: Int, startNote: Int, secondValue: Int): String {
        return if (gameMode == GAME_FIND_NOTE_GIVEN_INTERVAL || gameMode == GAME_FIND_NOTE_GIVEN_INTERVAL_REVERSED) {
            GameUtils.computeCorrectNote(getApplication(), gameMode, startNote, secondValue)
        } else { // randomGame == GAME_FIND_INTERVAL_GIVEN_NOTES
            GameUtils.computeRightInterval(getApplication(), gameMode, startNote, secondValue)
        }
    }

    companion object {

        const val GAME_FIND_NOTE_GIVEN_INTERVAL = 0 // L'interval de X est ...
        const val GAME_FIND_NOTE_GIVEN_INTERVAL_REVERSED = 1 // X est l'interval de ...
        const val GAME_FIND_INTERVAL_GIVEN_NOTES = 2 // Quelle est l'interval entre X et Y ?
    }
}
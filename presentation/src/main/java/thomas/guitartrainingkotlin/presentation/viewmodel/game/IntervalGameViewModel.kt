package thomas.guitartrainingkotlin.presentation.viewmodel.game

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.GameUtils
import thomas.guitartrainingkotlin.presentation.view.state.game.IntervalGameViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.AndroidStateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import kotlin.random.Random

class IntervalGameViewModel @ViewModelInject constructor(
    application: Application
) : AndroidStateViewModel<IntervalGameViewState>(application) {

    override val currentViewState = IntervalGameViewState()

    val gameReadyLiveEvent = SingleLiveEvent<Pair<Int, Triple<Int, Int, String>>>()

    init {
        getRandomValues()
    }

    fun getRandomValues() {
        val gameMode = Random.nextInt(GameUtils.NB_INTERVAL_GAMES)
        val startNote = Random.nextInt(ConstValues.NB_NOTES)
        val interval = Random.nextInt(GameUtils.NB_INTERVAL)
        val correctAnswer = computeAnswers(gameMode, startNote, interval)
        gameReadyLiveEvent.postValue(Pair(gameMode, Triple(startNote, interval, correctAnswer)))
    }

    fun computeFalseAnswers(correctAnswer: String): MutableList<String> {
        val falseAnswers = mutableListOf<String>()
        for (i in 0 until 3) {
            var interval = Random.nextInt(GameUtils.NB_INTERVAL)
            var newAnswer = GameUtils.computeFalseAnswers(getApplication(), interval)
            while (newAnswer == correctAnswer || falseAnswers.contains(newAnswer)) {
                interval = Random.nextInt(GameUtils.NB_INTERVAL)
                newAnswer = GameUtils.computeFalseAnswers(getApplication(), interval)
            }

            falseAnswers.add(newAnswer)
        }

        return falseAnswers
    }

    private fun computeAnswers(gameMode: Int, startNote: Int, secondValue: Int): String {
        return if (gameMode == GAME_FIND_NOTE_GIVEN_INTERVAL || gameMode == GAME_FIND_NOTE_GIVEN_INTERVAL_REVERSED) {
            GameUtils.computeCorrectNote(getApplication(), gameMode, startNote, secondValue)
        } else { // randomGame == GAME_FIND_INTERVAL_GIVEN_NOTES
            GameUtils.computeRightInterval(
                getApplication(),
                GAME_FIND_NOTE_GIVEN_INTERVAL,
                startNote,
                secondValue
            )
        }
    }

    companion object {
        const val GAME_FIND_NOTE_GIVEN_INTERVAL = 0 // L'interval de X est ...
        const val GAME_FIND_NOTE_GIVEN_INTERVAL_REVERSED = 1 // X est l'interval de ...
        const val GAME_FIND_INTERVAL_GIVEN_NOTES = 2 // Quelle est l'interval entre X et Y ?
    }
}
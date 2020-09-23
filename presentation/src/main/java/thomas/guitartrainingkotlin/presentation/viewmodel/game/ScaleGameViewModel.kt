package thomas.guitartrainingkotlin.presentation.viewmodel.game

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import thomas.guitartrainingkotlin.domain.model.game.Scale
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.GameUtils2
import thomas.guitartrainingkotlin.presentation.view.state.game.ScaleGameViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.AndroidStateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import kotlin.random.Random

class ScaleGameViewModel @ViewModelInject constructor(
    application: Application
) : AndroidStateViewModel<ScaleGameViewState>(application) {

    override val currentViewState = ScaleGameViewState()

    val answerCheckedLiveEvent = SingleLiveEvent<List<Boolean>>()
    val gameModeFindLiveEvent = SingleLiveEvent<Pair<Int, Scale>>()
    val gameModeIsCorrectLiveEvent = SingleLiveEvent<Triple<Int, Scale, Scale>>()

    init {
        getScaleGameMode()
    }

    fun getScaleGameMode() {

        val scaleGameMode = Random.nextInt(NB_SCALE_GAMES)
        val scaleIndex = Random.nextInt(ConstValues.NB_SCALES)
        val startNoteIndex = Random.nextInt(ConstValues.NB_NOTES)

        val correctScale = GameUtils2.computeCorrectScale(getApplication(), startNoteIndex, scaleIndex)

        when (scaleGameMode) {
            SCALE_GAME_FIND_NOTES -> {
                gameModeFindLiveEvent.postValue(
                    Pair(
                        scaleGameMode,
                        correctScale
                    )
                )
            }
            SCALE_GAME_FIND_SCALE -> {
                gameModeFindLiveEvent.postValue(
                    Pair(
                        scaleGameMode,
                        correctScale
                    )
                )
            }
            SCALE_GAME_IS_CORRECT_SCALE -> {
                val alteredScale = GameUtils2.alteredScale(getApplication(), correctScale)
                gameModeIsCorrectLiveEvent.postValue(
                    Triple(
                        scaleGameMode,
                        correctScale,
                        alteredScale
                    )
                )
            }
        }
    }

    fun checkAnswers(answersToCheck: List<String>, correctScale: Scale) {
        val resultList = mutableListOf<Boolean>()
        answersToCheck.forEachIndexed { index, s ->
            resultList.add(correctScale.notes[index] == s)
        }
        answerCheckedLiveEvent.postValue(
            resultList
        )
    }

    companion object {
        const val NB_SCALE_GAMES = 3
        const val SCALE_GAME_FIND_NOTES = 0
        const val SCALE_GAME_FIND_SCALE = 1
        const val SCALE_GAME_IS_CORRECT_SCALE = 2
    }
}
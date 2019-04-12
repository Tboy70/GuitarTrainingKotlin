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
    val finishRandomLiveEvent = SingleLiveEvent<Pair<Int, Int>>()

    init {
        getRandomValue()
    }

    fun getRandomValue() {
        finishRandomLiveEvent.postValue(
            Pair(
                Random().nextInt(ConstValues.NB_NOTES),
                Random().nextInt(ConstValues.NB_SCALES)
            )
        )
    }

    fun checkAnswers(
        answersList: List<String>,
        randomScale: String,
        randomNote: String
    ) {

        answerCheckedLiveEvent.postValue(
            GameUtils.checkScaleGameAnswer(
                answersList,
                randomScale,
                randomNote,
                getApplication()
            )
        )
    }

}
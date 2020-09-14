package thomas.guitartrainingkotlin.presentation.fragment.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_interval_game_2.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.viewmodel.game.IntervalGameViewModel2

@AndroidEntryPoint
class IntervalGameFragmentREfactor : Fragment(R.layout.fragment_interval_game_2) {

    private val viewModel by viewModels<IntervalGameViewModel2>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModelObservers()
    }

    private fun initViewModelObservers() {
        viewModel.randomizedGameLiveEvent.observeSafe(this) { gameMode ->
            if (gameMode == IntervalGameViewModel2.GAME_FIND_NOTE_GIVEN_INTERVAL) {
                fragment_interval_game_question.text = "GAME FIND NOTE GIVEN INTERVAL"
            } else if (gameMode == IntervalGameViewModel2.GAME_FIND_INTERVAL_GIVEN_NOTE) {
                fragment_interval_game_question.text = "GAME FIND INTERVAL GIVEN NOTE"
            }
        }
    }
}
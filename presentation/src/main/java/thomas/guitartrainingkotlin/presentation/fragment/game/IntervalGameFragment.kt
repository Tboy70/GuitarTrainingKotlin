package thomas.guitartrainingkotlin.presentation.fragment.game

import android.os.Bundle
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_interval_game.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.SnackbarComponent
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.viewmodel.game.IntervalGameViewModel
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class IntervalGameFragment : Fragment(R.layout.fragment_interval_game) {

    @Inject
    lateinit var dialogComponent: DialogComponent

    @Inject
    lateinit var snackbarComponent: SnackbarComponent

    private val viewModel by viewModels<IntervalGameViewModel>()

    private var gameMode: Int = 0
    private var givenNote: String = ""
    private var givenInterval: String = ""

    private val textChangedListener: TextWatcher = textChangedListener {
        updateConfirmButtonState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateToolbar()
        initiateViews()
        initiateViewModelObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(true)
        activity?.setSupportActionBar(fragment_interval_game_toolbar, ActivityExtensions.DISPLAY_UP)
    }

    private fun initiateViews() {
        fragment_interval_game_answer.addTextChangedListener(textChangedListener)
        fragment_interval_game_answer.setOnClickListener {
            dialogComponent.displaySingleListChoiceDialog(
                R.string.dialog_game_answer_title,
                R.array.list_notes,
                android.R.string.ok,
                onPositive = { selectedNote ->
                    fragment_interval_game_answer.setText(selectedNote)
                }
            )
        }

        fragment_interval_game_validate.setOnClickListener {
            viewModel.checkAnswer(
                givenNote,
                givenInterval,
                gameMode,
                fragment_interval_game_answer.getInput()
            )
        }
    }

    private fun initiateViewModelObservers() {
        viewModel.finishRandomLiveEvent.observeSafe(this) {
            givenNote = this.resources.getStringArray(R.array.list_notes)[it.first]
            givenInterval = this.resources.getStringArray(R.array.list_interval)[it.second]
            gameMode = it.third

            if (gameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE) {
                fragment_interval_game_question.text = activity?.getString(
                    R.string.interval_game_question,
                    givenInterval.substring(0, 1)
                        .toUpperCase(Locale.ROOT) + givenInterval.substring(1),
                    givenNote
                )
            } else if (gameMode == ConstValues.INTERVAL_REVERSED_GAME_MODE) {
                fragment_interval_game_question.text = activity?.getString(
                    R.string.interval_game_reversed_question,
                    givenNote,
                    givenInterval
                )
            }
        }

        viewModel.answerCheckedLiveEvent.observeSafe(this) { rightAnswer ->
            activity?.let { activity ->
                if (rightAnswer) {
                    snackbarComponent.displaySnackbar(
                        activity.findViewById(android.R.id.content),
                        getString(R.string.game_right_answer),
                        Snackbar.LENGTH_SHORT,
                        true
                    )
                    fragment_interval_game_answer.text = null
                    viewModel.getRandomValue()
                } else {
                    snackbarComponent.displaySnackbar(
                        activity.findViewById(android.R.id.content),
                        getString(R.string.game_wrong_answer),
                        Snackbar.LENGTH_SHORT,
                        false
                    )
                }
            }
        }
    }

    private fun updateConfirmButtonState() {
        fragment_interval_game_validate.isEnabled = fragment_interval_game_answer.isNotEmpty()
    }
}
package thomas.guitartrainingkotlin.presentation.fragment.game

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_interval_game.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.SnackbarComponent
import thomas.guitartrainingkotlin.presentation.extension.ActivityExtensions
import thomas.guitartrainingkotlin.presentation.extension.getInput
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.extension.setSupportActionBar
import thomas.guitartrainingkotlin.presentation.fragment.BaseFragment
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.viewmodel.game.IntervalGameViewModel
import javax.inject.Inject

class IntervalGameFragment : BaseFragment<IntervalGameViewModel>() {

    override val viewModelClass = IntervalGameViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_interval_game

    @Inject
    lateinit var dialogComponent: DialogComponent

    @Inject
    lateinit var snackbarComponent: SnackbarComponent

    private var gameMode: Int = 0
    private var randomNote: String = ""
    private var randomInterval: String = ""

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
        fragment_interval_game_answer.setOnClickListener {
            dialogComponent.displaySingleListChoiceDialog(
                R.string.dialog_interval_game_title,
                R.array.list_notes,
                android.R.string.ok,
                onPositive = { selectedNote ->
                    fragment_interval_game_answer.setText(selectedNote)
                }
            )
        }

        fragment_interval_game_validate.setOnClickListener {
            viewModel.checkAnswer(randomNote, randomInterval, gameMode, fragment_interval_game_answer.getInput())
        }
    }

    private fun initiateViewModelObservers() {
        viewModel.finishRandomLiveEvent.observeSafe(this) {
            randomNote = this.resources.getStringArray(R.array.list_notes)[it.first]
            randomInterval = this.resources.getStringArray(R.array.list_interval)[it.second]
            gameMode = it.third

            if (gameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE) {
                fragment_interval_game_question.text = activity?.getString(
                    R.string.interval_game_question,
                    randomInterval.substring(0, 1).toUpperCase() + randomInterval.substring(1),
                    randomNote
                )
            } else if (gameMode == ConstValues.INTERVAL_REVERSED_GAME_MODE) {
                fragment_interval_game_question.text = activity?.getString(
                    R.string.interval_game_reversed_question,
                    randomNote,
                    randomInterval
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

}
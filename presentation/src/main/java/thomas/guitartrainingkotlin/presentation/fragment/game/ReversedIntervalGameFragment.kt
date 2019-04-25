package thomas.guitartrainingkotlin.presentation.fragment.game

import android.os.Bundle
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_reversed_interval_game.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.SnackbarComponent
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.fragment.BaseFragment
import thomas.guitartrainingkotlin.presentation.viewmodel.game.ReversedIntervalGameViewModel
import javax.inject.Inject

class ReversedIntervalGameFragment : BaseFragment<ReversedIntervalGameViewModel>() {

    override val viewModelClass = ReversedIntervalGameViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_reversed_interval_game

    @Inject
    lateinit var dialogComponent: DialogComponent

    @Inject
    lateinit var snackbarComponent: SnackbarComponent

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
        activity?.setSupportActionBar(fragment_reversed_interval_game_toolbar, ActivityExtensions.DISPLAY_UP)
    }

    private fun initiateViews() {
        fragment_reversed_interval_game_answer.addTextChangedListener(textChangedListener)
        fragment_reversed_interval_game_answer.setOnClickListener {
            dialogComponent.displaySingleListChoiceDialog(
                R.string.dialog_game_answer_title,
                R.array.list_interval,
                android.R.string.ok,
                onPositive = { selectedNote ->
                    fragment_reversed_interval_game_answer.setText(selectedNote)
                }
            )
        }

        fragment_reversed_interval_game_validate.setOnClickListener {
            viewModel.checkAnswer(givenInterval, fragment_reversed_interval_game_answer.getInput())
        }
    }

    private fun initiateViewModelObservers() {
        viewModel.finishRandomLiveEvent.observeSafe(this) {
            givenInterval = this.resources.getStringArray(R.array.list_interval)[it]

            fragment_reversed_interval_game_question.text = activity?.getString(
                R.string.reversed_interval_game_question,
                givenInterval
            )
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
                    fragment_reversed_interval_game_answer.text = null
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
        fragment_reversed_interval_game_validate.isEnabled = fragment_reversed_interval_game_answer.isNotEmpty()
    }
}
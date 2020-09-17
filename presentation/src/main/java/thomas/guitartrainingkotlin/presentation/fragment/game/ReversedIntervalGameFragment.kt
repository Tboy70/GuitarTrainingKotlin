package thomas.guitartrainingkotlin.presentation.fragment.game

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reversed_interval_game.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.SnackbarComponent
import thomas.guitartrainingkotlin.presentation.extension.ActivityExtensions
import thomas.guitartrainingkotlin.presentation.extension.observeSafe
import thomas.guitartrainingkotlin.presentation.extension.setSupportActionBar
import thomas.guitartrainingkotlin.presentation.viewmodel.game.ReversedIntervalGameViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ReversedIntervalGameFragment : Fragment(R.layout.fragment_reversed_interval_game) {

    @Inject
    lateinit var dialogComponent: DialogComponent

    @Inject
    lateinit var snackbarComponent: SnackbarComponent

    private var correctAnswer: String = ""
    private var allSuggestedIntervalAnswers = mutableListOf<String>()

    private val viewModel by viewModels<ReversedIntervalGameViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initiateToolbar()
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
        activity?.setSupportActionBar(
            fragment_reversed_interval_game_toolbar,
            ActivityExtensions.DISPLAY_UP
        )
    }

    private fun initiateViewModelObservers() {
        viewModel.finishRandomLiveEvent.observeSafe(this) {
            correctAnswer = it.second

            // TODO : Maybe it's not a good practice --> See later
            allSuggestedIntervalAnswers = viewModel.computeFalseAnswers(correctAnswer)
            allSuggestedIntervalAnswers.add(correctAnswer)

            fillAnswerButtons(allSuggestedIntervalAnswers.toList().shuffled())

            fragment_reversed_interval_game_question.text = activity?.getString(
                R.string.reversed_interval_game_question,
                it.first
            )
        }
    }

    private fun fillAnswerButtons(answersList: List<String>) {
        reversed_interval_answer_1.apply {
            text = answersList[0]
            setOnClickListener {
                checkIntervalAnswer(text.toString())
            }
        }
        reversed_interval_answer_2.apply {
            text = answersList[1]
            setOnClickListener {
                checkIntervalAnswer(text.toString())
            }
        }
        reversed_interval_answer_3.apply {
            text = answersList[2]
            setOnClickListener {
                checkIntervalAnswer(text.toString())
            }
        }
        reversed_interval_answer_4.apply {
            text = answersList[3]
            setOnClickListener {
                checkIntervalAnswer(text.toString())
            }
        }
    }

    private fun checkIntervalAnswer(chosenInterval: String) {
        activity?.let { activity ->
            if (chosenInterval == correctAnswer) {
                snackbarComponent.displaySnackbar(
                    activity.findViewById(android.R.id.content),
                    getString(R.string.game_right_answer),
                    Snackbar.LENGTH_SHORT,
                    true
                )
                viewModel.getRandomValue()
            } else {
                snackbarComponent.displaySnackbar(
                    activity.findViewById(android.R.id.content),
                    getString(R.string.game_wrong_answer),
                    Snackbar.LENGTH_SHORT,
                    false
                )
            }
            allSuggestedIntervalAnswers.clear()
        }
    }
}
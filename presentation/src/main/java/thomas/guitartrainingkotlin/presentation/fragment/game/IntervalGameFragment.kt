package thomas.guitartrainingkotlin.presentation.fragment.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_interval_game.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.component.listener.SnackbarComponent
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.viewmodel.game.IntervalGameViewModel
import javax.inject.Inject

@AndroidEntryPoint
class IntervalGameFragment : Fragment(R.layout.fragment_interval_game) {

    @Inject
    lateinit var snackbarComponent: SnackbarComponent

    private val viewModel by viewModels<IntervalGameViewModel>()

    private var startNote: String = ""
    private var endNote: String = ""
    private var interval: String = ""

    private var correctAnswer: String = ""
    private var allSuggestedIntervalAnswers = mutableListOf<String>()

    private val textChangedListener: TextWatcher = textChangedListener {
        updateConfirmButtonState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModelObservers()
    }

    private fun initView() {
        initKeyboardAnswer()

        fragment_interval_game_answer.addTextChangedListener(textChangedListener)
        fragment_interval_game_delete_answer.setOnClickListener { fragment_interval_game_answer.text.clear() }

        fragment_interval_game_validate.setOnClickListener {
            activity?.let { activity ->

                if (fragment_interval_game_answer.text.toString() == correctAnswer) {
                    snackbarComponent.displaySnackbar(
                        activity.findViewById(android.R.id.content),
                        getString(R.string.game_right_answer),
                        Snackbar.LENGTH_SHORT,
                        true
                    )
                    viewModel.getRandomValues()

                } else {
                    snackbarComponent.displaySnackbar(
                        activity.findViewById(android.R.id.content),
                        getString(R.string.game_wrong_answer),
                        Snackbar.LENGTH_SHORT,
                        false
                    )
                }
                fragment_interval_game_answer.text.clear()
            }
        }
    }

    private fun initKeyboardAnswer() {
        fragment_interval_game_keyboard_C.setOnClickListener { fragment_interval_game_answer.append((it as Button).text) }
        fragment_interval_game_keyboard_D.setOnClickListener { fragment_interval_game_answer.append((it as Button).text) }
        fragment_interval_game_keyboard_E.setOnClickListener { fragment_interval_game_answer.append((it as Button).text) }
        fragment_interval_game_keyboard_F.setOnClickListener { fragment_interval_game_answer.append((it as Button).text) }
        fragment_interval_game_keyboard_G.setOnClickListener { fragment_interval_game_answer.append((it as Button).text) }
        fragment_interval_game_keyboard_A.setOnClickListener { fragment_interval_game_answer.append((it as Button).text) }
        fragment_interval_game_keyboard_B.setOnClickListener { fragment_interval_game_answer.append((it as Button).text) }
        fragment_interval_game_keyboard_sharp.setOnClickListener { fragment_interval_game_answer.append((it as Button).text) }
        fragment_interval_game_keyboard_flat.setOnClickListener { fragment_interval_game_answer.append((it as Button).text) }
    }

    @SuppressLint("DefaultLocale")
    private fun initViewModelObservers() {
        viewModel.gameReadyLiveEvent.observeSafe(this) { values -> // Pair(gameMode, Triple(startNote, interval, endNote))

            startNote = this.resources.getStringArray(R.array.list_notes_with_alterations)[values.second.first]

            when (values.first) {
                IntervalGameViewModel.GAME_FIND_NOTE_GIVEN_INTERVAL, IntervalGameViewModel.GAME_FIND_NOTE_GIVEN_INTERVAL_REVERSED -> {

                    fragment_interval_game_find_interval_layout.gone()
                    fragment_interval_game_find_note_layout.show()

                    correctAnswer = values.second.third
                    interval = this.resources.getStringArray(R.array.list_interval)[values.second.second]

                    if (values.first == IntervalGameViewModel.GAME_FIND_NOTE_GIVEN_INTERVAL) {
                        fragment_interval_game_question.text = getString(
                            R.string.interval_game_find_note_given_interval_question,
                            interval.capitalize(),
                            startNote
                        )
                    } else {
                        fragment_interval_game_question.text = getString(
                            R.string.interval_game_find_note_given_interval_reversed_question,
                            startNote,
                            interval
                        )
                    }
                }
                IntervalGameViewModel.GAME_FIND_INTERVAL_GIVEN_NOTES -> {
                    fragment_interval_game_find_interval_layout.show()
                    fragment_interval_game_find_note_layout.gone()

                    correctAnswer = this.resources.getStringArray(R.array.list_interval)[values.second.second]
                    endNote = values.second.third

                    allSuggestedIntervalAnswers = viewModel.computeFalseAnswers(correctAnswer)
                    allSuggestedIntervalAnswers.add(correctAnswer)

                    fillAnswerButtons(allSuggestedIntervalAnswers.toList().shuffled())

                    fragment_interval_game_question.text = getString(
                        R.string.interval_game_find_interval_given_notes,
                        startNote,
                        endNote
                    )
                }
            }
        }
    }

    private fun fillAnswerButtons(answersList: List<String>) {
        interval_answer_1.apply {
            text = answersList[0]
            setOnClickListener {
                checkIntervalAnswer(text.toString())
            }
        }
        interval_answer_2.apply {
            text = answersList[1]
            setOnClickListener {
                checkIntervalAnswer(text.toString())
            }
        }
        interval_answer_3.apply {
            text = answersList[2]
            setOnClickListener {
                checkIntervalAnswer(text.toString())
            }
        }
        interval_answer_4.apply {
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
                viewModel.getRandomValues()

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

    private fun updateConfirmButtonState() {
        fragment_interval_game_validate.isEnabled = fragment_interval_game_answer.isNotEmpty()
    }
}
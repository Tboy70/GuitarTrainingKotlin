package thomas.guitartrainingkotlin.presentation.fragment.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_interval_game_2.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.viewmodel.game.IntervalGameViewModel2

@AndroidEntryPoint
class IntervalGameFragmentRefactor : Fragment(R.layout.fragment_interval_game_2) {

    private val viewModel by viewModels<IntervalGameViewModel2>()

    private var gameMode: Int = 0
    private var givenBeginNote: String = ""
    private var givenEndNote: String = ""
    private var givenInterval: String = ""

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
        viewModel.randomizedGameLiveEvent.observeSafe(this) { values -> // game, beginNote, {interval or endNote}

            givenBeginNote =
                this.resources.getStringArray(R.array.list_notes_with_alterations)[values.second]

            when (values.first) {
                IntervalGameViewModel2.GAME_FIND_NOTE_GIVEN_INTERVAL -> {
                    fragment_interval_game_keyboard.show()
                    fragment_interval_game_validate.show()
                    fragment_interval_game_delete_answer.show()

                    givenInterval = this.resources.getStringArray(R.array.list_interval)[values.third]

                    fragment_interval_game_question.text = getString(
                        R.string.interval_game_find_note_given_interval_question,
                        givenInterval.capitalize(),
                        givenBeginNote
                    )
                }
                IntervalGameViewModel2.GAME_FIND_NOTE_GIVEN_INTERVAL_REVERSED -> {
                    fragment_interval_game_keyboard.show()
                    fragment_interval_game_validate.show()
                    fragment_interval_game_delete_answer.show()

                    givenInterval = this.resources.getStringArray(R.array.list_interval)[values.third]

                    fragment_interval_game_question.text = getString(
                        R.string.interval_game_find_note_given_interval_reversed_question,
                        givenBeginNote,
                        givenInterval
                    )
                }
                IntervalGameViewModel2.GAME_FIND_INTERVAL_GIVEN_NOTES -> {
                    fragment_interval_game_keyboard.gone()
                    fragment_interval_game_validate.gone()
                    fragment_interval_game_delete_answer.gone()

                    givenEndNote = this.resources.getStringArray(R.array.list_notes_with_alterations)[values.third]

                    fragment_interval_game_question.text = getString(
                        R.string.interval_game_find_interval_given_notes,
                        givenBeginNote,
                        givenEndNote
                    )
                }
            }
        }
    }

    private fun updateConfirmButtonState() {
        fragment_interval_game_validate.isEnabled = fragment_interval_game_answer.isNotEmpty()
    }
}
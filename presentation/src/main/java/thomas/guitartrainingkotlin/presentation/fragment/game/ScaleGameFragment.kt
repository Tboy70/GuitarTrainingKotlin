package thomas.guitartrainingkotlin.presentation.fragment.game

import android.app.Activity
import android.os.Bundle
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_scale_game.*
import kotlinx.android.synthetic.main.view_scale_degrees.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.domain.model.game.Scale
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.SnackbarComponent
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.viewmodel.game.ScaleGameViewModel
import java.util.*
import javax.inject.Inject

/**
 * Three scale games possible :
 * -> Find the scale giving notes
 * -> Find the note giving scale
 * -> Is the scale right giving note ?
 */
@AndroidEntryPoint
class ScaleGameFragment : Fragment(R.layout.fragment_scale_game_test) {

    @Inject
    lateinit var dialogComponent: DialogComponent

    @Inject
    lateinit var snackbarComponent: SnackbarComponent

    private val viewModel by viewModels<ScaleGameViewModel>()

    private val textChangedListener: TextWatcher = textChangedListener {
        updateConfirmButtonState()
    }

    private var gameMode: Int = 0
    private var returnedScale: Scale = Scale()
    private var listDegreesViews = mutableListOf<EditText>()

    private var isScaleCorrectAnswer: Boolean = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listDegreesViews.addAll(
            listOf(
                first_degree_answer, second_degree_answer, third_degree_answer,
                fourth_degree_answer, fifth_degree_answer, sixth_degree_answer,
                seventh_degree_answer, eight_degree_answer
            )
        )

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
        activity?.setSupportActionBar(fragment_scale_game_toolbar, ActivityExtensions.DISPLAY_UP)
    }

    private fun initiateViews() {
        initDegreesField()

        fragment_scale_game_which_scale_answer.addTextChangedListener(textChangedListener)

        fragment_scale_game_validate.setOnClickListener {
            if (gameMode == ScaleGameViewModel.SCALE_GAME_FIND_NOTES) {
                viewModel.checkAnswers(addAnswersToCheck(), returnedScale)
            } else if (gameMode == ScaleGameViewModel.SCALE_GAME_FIND_SCALE) {
                activity?.let { activity ->
                    if ((fragment_scale_game_which_scale_answer.getInput() == returnedScale.name)) {
                        displayAnswerUI(activity, true)
                    } else {
                        displayAnswerUI(activity, false)
                    }
                }
            }
        }

        fragment_scale_game_help.setOnClickListener {
            dialogComponent.displayCustomViewHelpScale(
                returnedScale.name,
                android.R.string.ok,
                onPositive = {
                    dialogComponent.dismissDialog()
                }
            )
        }

        fragment_scale_game_yes_button.setOnClickListener {
            activity?.let { activity ->
                if (isScaleCorrectAnswer) {
                    displayAnswerUI(activity, true)
                } else {
                    displayAnswerUI(activity, false)
                }
            }
        }

        fragment_scale_game_no_button.setOnClickListener {
            activity?.let { activity ->
                if (isScaleCorrectAnswer) {
                    displayAnswerUI(activity, false)
                } else {
                    displayAnswerUI(activity, true)
                }
            }
        }
    }

    private fun initiateViewModelObservers() {

        viewModel.gameModeFindLiveEvent.observeSafe(this) { gameModeAndScale ->
            gameMode = gameModeAndScale.first
            returnedScale = gameModeAndScale.second

            displayRequiredDegrees(returnedScale.name)
            displayUIElement(gameMode)

            if (gameMode == ScaleGameViewModel.SCALE_GAME_FIND_NOTES) {
                launchGameFindNotes(returnedScale.tonicNote.noteValue, returnedScale.name)
            } else if (gameMode == ScaleGameViewModel.SCALE_GAME_FIND_SCALE) {
                launchGameFindScale()
                fillDegreesValue(returnedScale)
            }
        }

        viewModel.gameModeIsCorrectLiveEvent.observeSafe(this) { gameModeAndScale ->
            gameMode = gameModeAndScale.first
            returnedScale = gameModeAndScale.second

            isScaleCorrectAnswer = gameModeAndScale.second.notes == gameModeAndScale.third.notes

            displayRequiredDegrees(returnedScale.name)
            displayUIElement(gameMode)
            launchGameIsScaleCorrect(returnedScale.tonicNote.noteValue, returnedScale.name)
            fillDegreesValue(gameModeAndScale.third)
        }

        viewModel.answerCheckedLiveEvent.observeSafe(this) { resultList ->
            activity?.let { activity ->
                if (resultList.all { it }) {
                    displayAnswerUI(activity, true)
                } else {
                    updateWrongAnswerUI(resultList)
                    displayAnswerUI(activity, false)
                }
            }
        }
    }

    private fun launchGameFindNotes(referenceNote: String, scale: String) {
        allowEditingDegreesValues(true)

        fragment_scale_game_description.text = activity?.getString(
            R.string.scale_game_first_mode_description
        )
        fragment_scale_game_question.text = activity?.getString(
            R.string.scale_game_question,
            referenceNote,
            scale.toLowerCase(Locale.ROOT)
        )
    }

    private fun launchGameFindScale() {
        allowEditingDegreesValues(false)

        fragment_scale_game_description.text = activity?.getString(
            R.string.scale_game_third_mode_description
        )

        fragment_scale_game_which_scale_answer.setOnClickListener {
            displayAnswerPopUpUI(fragment_scale_game_which_scale_answer, R.array.list_scales)
        }
    }

    private fun launchGameIsScaleCorrect(referenceNote: String, scale: String) {
        allowEditingDegreesValues(false)

        fragment_scale_game_description.text = activity?.getString(
            R.string.scale_game_second_mode_description
        )
        fragment_scale_game_question.text = activity?.getString(
            R.string.scale_game_question,
            referenceNote,
            scale.toLowerCase(Locale.ROOT)
        )
    }

    private fun initDegreesField() {
        listDegreesViews.forEach { field ->
            field.addTextChangedListener(textChangedListener)
            field.setOnClickListener {
                displayAnswerPopUpUI(field, R.array.list_all_notes_with_alterations)
            }
        }
    }

    private fun displayRequiredDegrees(scale: String) {
        context?.let { context ->
            if (scale == context.getString(R.string.tone_major) ||
                scale == context.getString(R.string.tone_minor_natural) ||
                scale == context.getString(R.string.tone_minor_harmonic) ||
                scale == context.getString(R.string.tone_minor_melodic)
            ) {
                seventh_degree_layout.show()
                eight_degree_layout.show()
            } else {
                eight_degree_layout.hide()
                if (scale == context.getString(R.string.tone_pentatonic_minor) || scale == context.getString(
                        R.string.tone_pentatonic_major
                    )
                ) {
                    seventh_degree_layout.hide()
                } else if (scale == context.getString(R.string.tone_blues_minor)) {
                    fourth_degree_label.setTextColor(
                        ContextCompat.getColor(
                            context,
                            android.R.color.holo_blue_dark
                        )
                    )
                    seventh_degree_layout.show()
                } else if (scale == context.getString(R.string.tone_blues_major)) {
                    third_degree_label.setTextColor(
                        ContextCompat.getColor(
                            context,
                            android.R.color.holo_blue_dark
                        )
                    )
                    seventh_degree_layout.show()
                }
            }
        }
    }

    private fun fillDegreesValue(scale: Scale) {
        displayRequiredDegrees(scale.name)
        first_degree_answer.setText(scale.notes[0])
        second_degree_answer.setText(scale.notes[1])
        third_degree_answer.setText(scale.notes[2])
        fourth_degree_answer.setText(scale.notes[3])
        fifth_degree_answer.setText(scale.notes[4])
        sixth_degree_answer.setText(scale.notes[5])
        if (scale.notes.size > 6) seventh_degree_answer.setText(scale.notes[6])
        if (scale.notes.size > 7) eight_degree_answer.setText(scale.notes[7])
    }

    private fun displayUIElement(gameMode: Int) {
        when (gameMode) {
            ScaleGameViewModel.SCALE_GAME_FIND_NOTES -> {
                fragment_scale_game_help.show()
                fragment_scale_game_question.show()
                fragment_scale_game_validate.show()
                fragment_scale_game_no_button.gone()
                fragment_scale_game_yes_button.gone()
                fragment_scale_game_which_scale_answer.gone()
            }
            ScaleGameViewModel.SCALE_GAME_IS_CORRECT_SCALE -> {
                fragment_scale_game_help.show()
                fragment_scale_game_question.show()
                fragment_scale_game_validate.gone()
                fragment_scale_game_no_button.show()
                fragment_scale_game_yes_button.show()
                fragment_scale_game_which_scale_answer.gone()
            }
            else -> {
                fragment_scale_game_help.gone()
                fragment_scale_game_question.gone()
                fragment_scale_game_validate.show()
                fragment_scale_game_no_button.gone()
                fragment_scale_game_yes_button.gone()
                fragment_scale_game_which_scale_answer.show()
            }
        }
    }

    private fun displayAnswerPopUpUI(field: EditText, answersChoiceArray: Int) {
        dialogComponent.displaySingleListChoiceDialog(
            R.string.dialog_game_answer_title,
            answersChoiceArray,
            android.R.string.ok,
            onPositive = { selectedAnswer ->
                field.setText(selectedAnswer)
                context?.let { context ->
                    field.setTextColor(ContextCompat.getColor(context, android.R.color.black))
                }
            }
        )
    }

    private fun addAnswersToCheck(): List<String> {
        val answersList = mutableListOf<String>()
        answersList.addAll(
            listOf(
                first_degree_answer.getInput(), second_degree_answer.getInput(),
                third_degree_answer.getInput(), fourth_degree_answer.getInput(),
                fifth_degree_answer.getInput(), sixth_degree_answer.getInput()
            )
        )

        if (returnedScale.name == context?.getString(R.string.tone_minor_natural) ||
            returnedScale.name == context?.getString(R.string.tone_minor_harmonic) ||
            returnedScale.name == context?.getString(R.string.tone_minor_melodic) ||
            returnedScale.name == context?.getString(R.string.tone_major)
        ) {
            answersList.add(seventh_degree_answer.getInput())
            answersList.add(eight_degree_answer.getInput())
        } else if (returnedScale.name == context?.getString(R.string.tone_blues_minor) ||
            returnedScale.name == context?.getString(R.string.tone_blues_major)
        ) {
            answersList.add(seventh_degree_answer.getInput())
        }

        return answersList.toList()
    }

    private fun displayAnswerUI(activity: Activity, correct: Boolean) {
        snackbarComponent.displaySnackbar(
            activity.findViewById(android.R.id.content),
            if (correct) getString(R.string.game_right_answer) else getString(R.string.game_right_answer),
            Snackbar.LENGTH_SHORT,
            correct
        )

        if (correct) {
            resetGame()
        }
    }

    private fun updateWrongAnswerUI(resultList: List<Boolean>) {
        context?.let { context ->
            resultList.forEachIndexed { index, rightAnswer ->
                getAssociatedEditTextToAnswerIndex(index).setTextColor(
                    ContextCompat.getColor(
                        context,
                        if (!rightAnswer) android.R.color.holo_red_dark else android.R.color.holo_green_dark
                    )
                )
            }
        }
    }

    private fun allowEditingDegreesValues(allowedEditing: Boolean) {

        listDegreesViews.forEach { degreeView ->
            if (!allowedEditing) {
                degreeView.isFocusableInTouchMode = allowedEditing
                degreeView.isFocusable = allowedEditing
            }

            degreeView.isEnabled = allowedEditing
            degreeView.isCursorVisible = allowedEditing
        }
    }

    private fun resetGame() {
        listDegreesViews.forEach { degreeView ->
            degreeView.text = null
            context?.let { context ->
                degreeView.setTextColor(ContextCompat.getColor(context, android.R.color.black))
                fourth_degree_label.setTextColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.tab_indicator_text
                    )
                )
                third_degree_label.setTextColor(
                    ContextCompat.getColor(
                        context,
                        android.R.color.tab_indicator_text
                    )
                )
            }
        }
        fragment_scale_game_which_scale_answer.text = null
        viewModel.getScaleGameMode()
    }

    private fun getAssociatedEditTextToAnswerIndex(answerIndex: Int): EditText {
        return when (answerIndex) {
            0 -> first_degree_answer
            1 -> second_degree_answer
            2 -> third_degree_answer
            3 -> fourth_degree_answer
            4 -> fifth_degree_answer
            5 -> sixth_degree_answer
            6 -> seventh_degree_answer
            7 -> eight_degree_answer
            else -> first_degree_answer
        }
    }

    private fun updateConfirmButtonState() {
        val fieldAreNotBlank = fieldsAreNotBlank()
        fragment_scale_game_validate.isEnabled = fieldAreNotBlank
    }

    private fun fieldsAreNotBlank(): Boolean {
        val mandatoryDegree =
            first_degree_answer.isNotEmpty() && second_degree_answer.isNotEmpty() &&
                    third_degree_answer.isNotEmpty() && fourth_degree_answer.isNotEmpty() &&
                    fifth_degree_answer.isNotEmpty() && sixth_degree_answer.isNotEmpty()

        var optionalDegree = true
        if (seventh_degree_layout.visibility == View.VISIBLE && eight_degree_layout.visibility == View.VISIBLE) {
            optionalDegree = seventh_degree_answer.isNotEmpty() && eight_degree_answer.isNotEmpty()
        }

        var answerFilled = true
        if (fragment_scale_game_which_scale_answer.visibility == View.VISIBLE && !fragment_scale_game_which_scale_answer.isNotEmpty()) {
            answerFilled = false
        }

        return mandatoryDegree && optionalDegree && answerFilled
    }
}
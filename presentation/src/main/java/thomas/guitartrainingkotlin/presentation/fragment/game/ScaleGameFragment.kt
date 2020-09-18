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
class ScaleGameFragment : Fragment(R.layout.fragment_scale_game) {

    @Inject
    lateinit var dialogComponent: DialogComponent

    @Inject
    lateinit var snackbarComponent: SnackbarComponent

    private val viewModel by viewModels<ScaleGameViewModel>()

    private val textChangedListener: TextWatcher = textChangedListener {
        updateConfirmButtonState()
    }

    private var listDegreesViews = mutableListOf<EditText>()

    private var referenceNote: String = ""
    private var scale: String = ""
    private var gameMode: Int = 0

    private var isScaleCorrectAnswer: String = ""

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

        fragment_scale_game_validate.setOnClickListener {
            if (gameMode == ScaleGameViewModel.SCALE_GAME_FIND_NOTES) {
                viewModel.checkAnswers(addAnswersToCheck(), scale, referenceNote)
            } else if (gameMode == ScaleGameViewModel.SCALE_GAME_FIND_SCALE) {
                activity?.let { activity ->
                    if (fragment_scale_game_which_scale_answer.getInput() == isScaleCorrectAnswer) {
                        displayCorrectAnswerUI(activity)
                    } else {
                        displayWrongAnswerUI(activity)
                    }
                }
            }
        }

        fragment_scale_game_help.setOnClickListener {
            dialogComponent.displayCustomViewHelpScale(
                scale,
                android.R.string.ok,
                onPositive = {
                    dialogComponent.dismissDialog()
                }
            )
        }

        fragment_scale_game_yes_button.setOnClickListener {
            viewModel.checkAnswers(addAnswersToCheck(), scale, referenceNote, true)
        }

        fragment_scale_game_no_button.setOnClickListener {
            viewModel.checkAnswers(addAnswersToCheck(), scale, referenceNote, false)
        }
    }

    private fun initiateViewModelObservers() {

        /** The game has been selected.
         * it.first -> Random from 1 to 12 (which note)
         * it.second -> Random from 1 to 8 (which scale)
         * it.third -> Random from 1 to 3 (which game)
         **/
        viewModel.gameModeLiveEvent.observeSafe(this) { values -> // Triple(note : Int, scale : Int, gameMode : Int)

            referenceNote = this.resources.getStringArray(R.array.list_notes_with_alterations)[values.first]
            scale = this.resources.getStringArray(R.array.list_scales)[values.second]
            gameMode = values.third

            displayRequiredDegrees(scale)
            displayValidateButton(gameMode)

            when (values.third) {
                ScaleGameViewModel.SCALE_GAME_FIND_NOTES -> {
                    launchGameFindNotes(referenceNote, scale)
                }
                ScaleGameViewModel.SCALE_GAME_IS_CORRECT_SCALE -> {
                    launchGameIsScaleCorrect(referenceNote, scale)
                }
                else -> {
                    launchGameFindScale(referenceNote)
                }
            }

//            referenceNote = this.resources.getStringArray(R.array.list_notes_with_alterations)[it.first]
//            scale = this.resources.getStringArray(R.array.list_scales)[it.second]
//            gameMode = it.third
//
//            displayRequiredDegrees(scale)
//            displayValidateButton(gameMode)
//
//            when (gameMode) {
//                ConstValues.SCALE_GAME_FIND_NOTES -> {
//                    launchGameFindNotes(referenceNote, scale)
//                }
//                ConstValues.SCALE_GAME_IS_CORRECT_SCALE -> {
//                    launchGameIsScaleCorrect(referenceNote, scale)
//                }
//                else -> {
//                    launchGameFindScale(referenceNote)
//                }
//            }
        }

        viewModel.answerCheckedLiveEvent.observeSafe(this) { resultList ->
            activity?.let { activity ->
                if (resultList.all { it }) {
                    displayCorrectAnswerUI(activity)
                } else {
                    updateWrongAnswerUI(resultList)
                    displayWrongAnswerUI(activity)
                }
            }
        }

        viewModel.answerCheckedWithUserChoiceLiveEvent.observeSafe(this) { pairResultAndUserAnswer ->
            activity?.let { activity ->
                if (pairResultAndUserAnswer.first.all { it }) {
                    if (pairResultAndUserAnswer.second) {
                        displayCorrectAnswerUI(activity)
                    } else {
                        updateWrongAnswerUI(pairResultAndUserAnswer.first)
                        displayWrongAnswerUI(activity)
                    }
                } else {
                    if (pairResultAndUserAnswer.second) {
                        updateWrongAnswerUI(pairResultAndUserAnswer.first)
                        displayWrongAnswerUI(activity)
                    } else {
                        displayCorrectAnswerUI(activity)
                    }
                }
            }
        }

        viewModel.generatedRandomScaleLiveEvent.observeSafe(this) { pairScaleAndName ->
            fillDegreesValue(pairScaleAndName)
        }

        viewModel.correctScaleLiveEvent.observeSafe(this) { pairScaleAndName ->
            fillDegreesValue(pairScaleAndName)
            isScaleCorrectAnswer = pairScaleAndName.second
        }
    }

    private fun fillDegreesValue(pairScaleAndName: Pair<List<String>, String>) {
        displayRequiredDegrees(pairScaleAndName.second)
        first_degree_answer.setText(pairScaleAndName.first[0])
        second_degree_answer.setText(pairScaleAndName.first[1])
        third_degree_answer.setText(pairScaleAndName.first[2])
        fourth_degree_answer.setText(pairScaleAndName.first[3])
        fifth_degree_answer.setText(pairScaleAndName.first[4])
        sixth_degree_answer.setText(pairScaleAndName.first[5])
        if (pairScaleAndName.first.size > 6) seventh_degree_answer.setText(pairScaleAndName.first[6])
        if (pairScaleAndName.first.size > 7) eight_degree_answer.setText(pairScaleAndName.first[7])
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

    private fun displayValidateButton(gameMode: Int) {
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

        viewModel.generateRandomScale(referenceNote, scale)
    }

    private fun launchGameFindScale(referenceNote: String) {
        allowEditingDegreesValues(false)

        fragment_scale_game_description.text = activity?.getString(
            R.string.scale_game_third_mode_description
        )

        fragment_scale_game_which_scale_answer.setOnClickListener {
            displayAnswerPopUpUI(fragment_scale_game_which_scale_answer, R.array.list_scales)
        }

        viewModel.generateCorrectScale(referenceNote)
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

        if (scale == context?.getString(R.string.tone_minor_natural) ||
            scale == context?.getString(R.string.tone_minor_harmonic) ||
            scale == context?.getString(R.string.tone_minor_melodic) ||
            scale == context?.getString(R.string.tone_major)
        ) {
            answersList.add(seventh_degree_answer.getInput())
            answersList.add(eight_degree_answer.getInput())
        } else if (scale == context?.getString(R.string.tone_blues_minor) ||
            scale == context?.getString(R.string.tone_blues_major)
        ) {
            answersList.add(seventh_degree_answer.getInput())
        }

        return answersList.toList()
    }

    private fun displayCorrectAnswerUI(activity: Activity) {
        snackbarComponent.displaySnackbar(
            activity.findViewById(android.R.id.content),
            getString(R.string.game_right_answer),
            Snackbar.LENGTH_SHORT,
            true
        )
        resetGame()
    }

    private fun displayWrongAnswerUI(activity: Activity) {
        snackbarComponent.displaySnackbar(
            activity.findViewById(android.R.id.content),
            getString(R.string.game_wrong_answer),
            Snackbar.LENGTH_SHORT,
            false
        )
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

        return mandatoryDegree && optionalDegree
    }
}
package thomas.guitartrainingkotlin.presentation.fragment.game

import android.app.Activity
import android.os.Bundle
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_scale_game.*
import kotlinx.android.synthetic.main.view_scale_degrees.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.component.listener.DialogComponent
import thomas.guitartrainingkotlin.presentation.component.listener.SnackbarComponent
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.fragment.BaseFragment
import thomas.guitartrainingkotlin.presentation.viewmodel.game.ScaleGameViewModel
import javax.inject.Inject

class ScaleGameFragment : BaseFragment<ScaleGameViewModel>() {

    override val viewModelClass = ScaleGameViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_scale_game

    @Inject
    lateinit var dialogComponent: DialogComponent

    @Inject
    lateinit var snackbarComponent: SnackbarComponent

    private val textChangedListener: TextWatcher = textChangedListener {
        updateConfirmButtonState()
    }

    private var givenNote: String = ""
    private var givenScale: String = ""
    private var givenGameMode: Int = 0

    private var thirdGameCorrectAnswer: String = ""    // TODO : In view model ?

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
        activity?.setSupportActionBar(fragment_scale_game_toolbar, ActivityExtensions.DISPLAY_UP)
    }

    private fun initiateViews() {
        first_degree_answer.addTextChangedListener(textChangedListener)
        second_degree_answer.addTextChangedListener(textChangedListener)
        third_degree_answer.addTextChangedListener(textChangedListener)
        fourth_degree_answer.addTextChangedListener(textChangedListener)
        fifth_degree_answer.addTextChangedListener(textChangedListener)
        sixth_degree_answer.addTextChangedListener(textChangedListener)
        seventh_degree_answer.addTextChangedListener(textChangedListener)
        eight_degree_answer.addTextChangedListener(textChangedListener)

        fragment_scale_game_validate.setOnClickListener {

            if (givenGameMode == 0) {
                viewModel.checkAnswers(addAnswersToCheck(), givenScale, givenNote)
            } else if (givenGameMode == 3) {
                activity?.let { activity ->
                    if (fragment_scale_game_which_scale_answer.getInput() == thirdGameCorrectAnswer) {
                        displayCorrectAnswerUI(activity)
                    } else {
                        displayWrongAnswerUI(activity)
                    }
                }
            }
        }

        fragment_scale_game_help.setOnClickListener {
            dialogComponent.displayCustomViewHelpScale(
                givenScale,
                android.R.string.ok,
                onPositive = {
                    dialogComponent.dismissDialog()
                }
            )
        }

        initAnswersFields(
            first_degree_answer, second_degree_answer, third_degree_answer,
            fourth_degree_answer, fifth_degree_answer, sixth_degree_answer,
            seventh_degree_answer, eight_degree_answer
        )
    }

    private fun initiateViewModelObservers() {
        viewModel.finishRandomLiveEvent.observeSafe(this) {
            givenGameMode = it.third
            givenNote = this.resources.getStringArray(R.array.list_notes)[it.first]
            givenScale = this.resources.getStringArray(R.array.list_scales)[it.second]

            context?.let { context ->
                third_degree_label.setTextColor(ContextCompat.getColor(context, android.R.color.black))
                fourth_degree_label.setTextColor(ContextCompat.getColor(context, android.R.color.black))
            }

            displayRequiredDegrees(givenScale)

            when (givenGameMode) {
                1 -> {
                    displayValidateButton(true)
                    launchFirstGameMode(givenNote, givenScale) // User give all the notes
                }
                2 -> {
                    displayValidateButton(false)
                    launchSecondGameMode(givenNote, givenScale) // User YES / NO
                }
                else -> {
                    displayValidateButton(true)
                    launchThirdGameMode(givenNote)  // User find the right scale
                }
            }
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

        viewModel.correctScaleLiveEvent.observeSafe(this) {
            first_degree_answer.setText(it.first[0])
            second_degree_answer.setText(it.first[1])
            third_degree_answer.setText(it.first[2])
            fourth_degree_answer.setText(it.first[3])
            fifth_degree_answer.setText(it.first[4])
            sixth_degree_answer.setText(it.first[5])
            seventh_degree_answer.setText(it.first[6])
            eight_degree_answer.setText(it.first[7])

            thirdGameCorrectAnswer = it.second
        }
    }

    private fun initAnswersFields(vararg fields: EditText) {
        fields.forEach { field ->
            field.setOnClickListener {
                displayAnswerPopUpUI(field, R.array.list_notes)
            }
        }
    }

    private fun displayRequiredDegrees(givenScale: String) {
        context?.let { context ->
            if (givenScale == context.getString(R.string.tone_major) || givenScale == context.getString(R.string.tone_minor)) {
                eight_degree_layout.show()
            } else {
                eight_degree_layout.hide()
                if (givenScale == context.getString(R.string.tone_pentatonic_minor) || givenScale == context.getString(R.string.tone_pentatonic_major)) {
                    seventh_degree_layout.hide()
                } else if (givenScale == context.getString(R.string.tone_blues_minor)) {
                    fourth_degree_label.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark))
                    seventh_degree_layout.show()
                } else if (givenScale == context.getString(R.string.tone_blues_major)) {
                    third_degree_label.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark))
                    seventh_degree_layout.show()
                }
            }
        }
    }

    private fun displayValidateButton(validateButtonVisible: Boolean) {
        if (validateButtonVisible) {
            fragment_scale_game_help.show()
            fragment_scale_game_validate.show()
            fragment_scale_game_question.show()
            fragment_scale_fragment_buttons_layout.gone()
            fragment_scale_game_which_scale_answer.gone()
        } else {
            fragment_scale_game_help.gone()
            fragment_scale_game_validate.gone()
            fragment_scale_game_question.gone()
            fragment_scale_fragment_buttons_layout.show()
            fragment_scale_game_which_scale_answer.show()
        }
    }

    private fun launchFirstGameMode(givenNote: String, givenScale: String) {
        fragment_scale_game_description.text = activity?.getString(
            R.string.scale_game_first_mode_description
        )

        fragment_scale_game_question.text = activity?.getString(
            R.string.scale_game_question,
            givenNote,
            givenScale.toLowerCase()
        )
    }

    private fun launchSecondGameMode(givenNote: String, givenScale: String) {
        fragment_scale_game_description.text = activity?.getString(
            R.string.scale_game_second_mode_description
        )

        fragment_scale_game_question.text = activity?.getString(
            R.string.scale_game_question,
            givenNote,
            givenScale.toLowerCase()
        )

        viewModel.generateRandomScale(givenNote)
    }

    private fun launchThirdGameMode(givenNote: String) {
        fragment_scale_game_description.text = activity?.getString(
            R.string.scale_game_third_mode_description
        )

        fragment_scale_game_which_scale_answer.setOnClickListener {
            displayAnswerPopUpUI(fragment_scale_game_which_scale_answer, R.array.list_scales)
        }

        viewModel.generateCorrectScale(givenNote)
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
        answersList.add(first_degree_answer.getInput())
        answersList.add(second_degree_answer.getInput())
        answersList.add(third_degree_answer.getInput())
        answersList.add(fourth_degree_answer.getInput())
        answersList.add(fifth_degree_answer.getInput())
        answersList.add(sixth_degree_answer.getInput())

        if (givenScale == context?.getString(R.string.tone_minor) ||
            givenScale == context?.getString(R.string.tone_major)
        ) {
            answersList.add(seventh_degree_answer.getInput())
            answersList.add(eight_degree_answer.getInput())
        } else if (givenScale == context?.getString(R.string.tone_blues_minor) ||
            givenScale == context?.getString(R.string.tone_blues_major)
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

    private fun updateWrongAnswerUI(resultList: List<Boolean>?) {
        context?.let { context ->
            resultList?.let { answers ->
                answers.forEachIndexed { index, rightAnswer ->
                    getAssociatedEditTextToAnswerIndex(index).setTextColor(
                        ContextCompat.getColor(
                            context,
                            if (!rightAnswer) android.R.color.holo_red_dark else android.R.color.holo_green_dark
                        )
                    )
                }
            }
        }
    }

    private fun resetGame() {
        first_degree_answer.text = null
        second_degree_answer.text = null
        third_degree_answer.text = null
        fourth_degree_answer.text = null
        fifth_degree_answer.text = null
        sixth_degree_answer.text = null
        seventh_degree_answer.text = null
        eight_degree_answer.text = null
        viewModel.getRandomValue()
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
        val mandatoryDegree = first_degree_answer.isNotEmpty() && second_degree_answer.isNotEmpty() &&
                third_degree_answer.isNotEmpty() && fourth_degree_answer.isNotEmpty() &&
                fifth_degree_answer.isNotEmpty() && sixth_degree_answer.isNotEmpty()

        var optionalDegree = true
        if (seventh_degree_layout.visibility == View.VISIBLE && eight_degree_layout.visibility == View.VISIBLE) {
            optionalDegree = seventh_degree_answer.isNotEmpty() && eight_degree_answer.isNotEmpty()
        }

        return mandatoryDegree && optionalDegree
    }
}
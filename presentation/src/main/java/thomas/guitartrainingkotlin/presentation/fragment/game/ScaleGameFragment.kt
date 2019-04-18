package thomas.guitartrainingkotlin.presentation.fragment.game

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
            val answersList = mutableListOf<String>()
            answersList.add(first_degree_answer.getInput())
            answersList.add(second_degree_answer.getInput())
            answersList.add(third_degree_answer.getInput())
            answersList.add(fourth_degree_answer.getInput())
            answersList.add(fifth_degree_answer.getInput())

            if (givenScale == context?.getString(R.string.tone_minor) || givenScale == context?.getString(R.string.tone_major)) {
                answersList.add(sixth_degree_answer.getInput())
                answersList.add(seventh_degree_answer.getInput())
                answersList.add(eight_degree_answer.getInput())
            } else if (givenScale == context?.getString(R.string.tone_blues)) {
                answersList.add(sixth_degree_answer.getInput())
                answersList.add(seventh_degree_answer.getInput())
            }

            viewModel.checkAnswers(
                answersList.toList(),
                givenScale,
                givenNote
            )
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
            first_degree_answer,
            second_degree_answer,
            third_degree_answer,
            fourth_degree_answer,
            fifth_degree_answer,
            sixth_degree_answer,
            seventh_degree_answer,
            eight_degree_answer
        )
    }

    private fun initiateViewModelObservers() {
        viewModel.finishRandomLiveEvent.observeSafe(this) {
            givenNote = this.resources.getStringArray(R.array.list_notes)[it.first]
            givenScale = this.resources.getStringArray(R.array.list_scales)[it.second]

            if (givenScale == context?.getString(R.string.tone_pentatonic_minor) || givenScale == context?.getString(
                    R.string.tone_pentatonic_major
                )
            ) {
                sixth_degree_layout.hide()
                seventh_degree_layout.hide()
                eight_degree_layout.hide()
            } else if (givenScale == context?.getString(R.string.tone_blues)) {
                context?.let { context ->
                    fourth_degree_label.setTextColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark))
                }
                sixth_degree_layout.show()
                seventh_degree_layout.show()
                eight_degree_layout.hide()
            } else {
                sixth_degree_layout.show()
                seventh_degree_layout.show()
                eight_degree_layout.show()
            }

            fragment_scale_game_question.text = activity?.getString(
                R.string.scale_game_question,
                givenNote,
                givenScale.toLowerCase()
            )
        }

        viewModel.answerCheckedLiveEvent.observeSafe(this) { resultList ->
            activity?.let { activity ->
                if (resultList.all { it }) {
                    snackbarComponent.displaySnackbar(
                        activity.findViewById(android.R.id.content),
                        getString(R.string.game_right_answer),
                        Snackbar.LENGTH_SHORT,
                        true
                    )
                    first_degree_answer.text = null
                    second_degree_answer.text = null
                    third_degree_answer.text = null
                    fourth_degree_answer.text = null
                    fifth_degree_answer.text = null
                    sixth_degree_answer.text = null
                    seventh_degree_answer.text = null
                    eight_degree_answer.text = null
                    viewModel.getRandomValue()
                } else {
                    updateWrongAnswerUI(resultList)
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

    private fun initAnswersFields(vararg fields: EditText) {
        fields.forEach { field ->
            field.setOnClickListener {
                dialogComponent.displaySingleListChoiceDialog(
                    R.string.dialog_interval_game_title,
                    R.array.list_notes,
                    android.R.string.ok,
                    onPositive = { selectedNote ->
                        field.setText(selectedNote)
                        context?.let { context ->
                            field.setTextColor(ContextCompat.getColor(context, android.R.color.black))
                        }
                    }
                )
            }
        }
    }

    private fun updateWrongAnswerUI(resultList: List<Boolean>?) {
        context?.let { context ->
            resultList?.let { answers ->
                answers.forEachIndexed { index, rightAnswer ->
                    if (!rightAnswer) {
                        getAssociatedEditTextToAnswerIndex(index).setTextColor(
                            ContextCompat.getColor(
                                context,
                                android.R.color.holo_red_dark
                            )
                        )
                    } else {
                        getAssociatedEditTextToAnswerIndex(index).setTextColor(
                            ContextCompat.getColor(
                                context,
                                android.R.color.holo_green_dark
                            )
                        )
                    }
                }
            }
        }
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
        val mandatoryDegree = first_degree_answer.isNotEmpty() &&
                second_degree_answer.isNotEmpty() &&
                third_degree_answer.isNotEmpty() &&
                fourth_degree_answer.isNotEmpty() &&
                fifth_degree_answer.isNotEmpty()

        var optionalDegree = true
        if (sixth_degree_layout.visibility == View.VISIBLE && seventh_degree_layout.visibility == View.VISIBLE && eight_degree_layout.visibility == View.VISIBLE) {
            optionalDegree =
                sixth_degree_answer.isNotEmpty() && seventh_degree_answer.isNotEmpty() && eight_degree_answer.isNotEmpty()
        }

        return mandatoryDegree && optionalDegree
    }
}
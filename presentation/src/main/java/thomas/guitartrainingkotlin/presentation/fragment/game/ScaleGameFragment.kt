package thomas.guitartrainingkotlin.presentation.fragment.game

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
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

    private var randomNote: String = ""
    private var randomScale: String = ""

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
        fragment_scale_game_validate.setOnClickListener {
            val answersList = mutableListOf<String>()
            answersList.add(first_degree_answer.getInput())
            answersList.add(second_degree_answer.getInput())
            answersList.add(third_degree_answer.getInput())
            answersList.add(fourth_degree_answer.getInput())
            answersList.add(fifth_degree_answer.getInput())
            if (randomScale != context?.getString(R.string.tone_pentatonic_minor) && randomScale != context?.getString(R.string.tone_pentatonic_major)) {
                answersList.add(sixth_degree_answer.getInput())
                answersList.add(seventh_degree_answer.getInput())
            }
            viewModel.checkAnswers(
                answersList.toList(),
                randomScale,
                randomNote
            )
        }

        fragment_scale_game_help.setOnClickListener {
            dialogComponent.displayCustomViewHelpScale(
                randomScale,
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
            seventh_degree_answer
        )
    }

    private fun initiateViewModelObservers() {
        viewModel.finishRandomLiveEvent.observeSafe(this) {
            randomNote = this.resources.getStringArray(R.array.list_notes)[it.first]
            randomScale = this.resources.getStringArray(R.array.list_scales)[it.second]

            if (randomScale == context?.getString(R.string.tone_pentatonic_minor) || randomScale == context?.getString(
                    R.string.tone_pentatonic_major
                )
            ) {
                sixth_degree_layout.hide()
                seventh_degree_layout.hide()
            }

            fragment_scale_game_question.text = activity?.getString(
                R.string.scale_game_question,
                randomNote,
                randomScale.toLowerCase()
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

    private fun initAnswersFields(vararg fields: EditText) {
        fields.forEach { field ->
            field.setOnClickListener {
                dialogComponent.displaySingleListChoiceDialog(
                    R.string.dialog_interval_game_title,
                    R.array.list_notes,
                    android.R.string.ok,
                    onPositive = { selectedNote ->
                        field.setText(selectedNote)
                    }
                )
            }
        }
    }
}
package thomas.guitartrainingkotlin.presentation.ui.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_user_programs_list_item.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.utils.DateTimeUtils
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import java.util.*

class ProgramListViewHolder(
    itemView: View,
    private val context: Context
) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    private val currentView: View = itemView

    fun bind(
        programViewDataWrapper: ProgramViewDataWrapper,
        onProgramSelectedListener: (programId: String, view: View) -> Unit
    ) {

        view_user_programs_list_item_container.transitionName = programViewDataWrapper.getId()

        view_user_programs_list_item_name.text = programViewDataWrapper.getName()

        view_user_programs_list_item_nb_exercises.text = String.format(
            Locale.FRANCE,
            context.getString(R.string.user_programs_list_nb_exercises_text),
            programViewDataWrapper.getExercises().size.toString()
        )

        val totalDurationProgram = calculateTotalDurationProgram(programViewDataWrapper)

        (totalDurationProgram < DateTimeUtils.SECONDS_IN_ONE_MINUTE).let {
            if (it) {
                view_user_programs_list_item_total_duration_exercises.text = String.format(
                    Locale.FRANCE,
                    context.getString(R.string.fragment_user_programs_list_total_duration_exercises_minutes_text),
                    totalDurationProgram
                )
            } else {
                val hours: Int = totalDurationProgram / DateTimeUtils.SECONDS_IN_ONE_MINUTE.toInt()
                val minutes: Int =
                    totalDurationProgram % DateTimeUtils.SECONDS_IN_ONE_MINUTE.toInt()
                view_user_programs_list_item_total_duration_exercises.text = String.format(
                    Locale.FRANCE,
                    context.getString(R.string.fragment_user_programs_list_total_duration_exercises_hours_text),
                    hours.toString(), minutes.toString()
                )
            }
        }

        currentView.setOnClickListener {
            onProgramSelectedListener(programViewDataWrapper.getId(), view_user_programs_list_item_container)
        }
    }

    private fun calculateTotalDurationProgram(programViewDataWrapper: ProgramViewDataWrapper): Int {
        var totalDuration = 0
        programViewDataWrapper.getExercises().forEach { exercise ->
            totalDuration += exercise.durationExercise
        }
        return totalDuration
    }

}
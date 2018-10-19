package thomas.example.com.guitarTrainingKotlin.ui.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_user_programs_list_item.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserProgramsListAdapterListener
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.ProgramViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.utils.DateTimeUtils
import java.util.*

class ProgramViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView), LayoutContainer {
    override val containerView: View?
        get() = itemView

    private val currentView: View = itemView

    // TODO : Format in view data wrapper instead !!
    fun bindProgram(programViewDataWrapper: ProgramViewDataWrapper, userProgramsListAdapterListener: UserProgramsListAdapterListener) {
        view_user_programs_list_item_name.text = programViewDataWrapper.getName()
        view_user_programs_list_item_nb_exercises.text = String.format(
            Locale.FRANCE,
            context.getString(R.string.user_programs_list_nb_exercises_text),
            programViewDataWrapper.getExercises().size.toString()
        )

        val totalDurationProgram: Int = calculateTotalDurationProgram(programViewDataWrapper)
        if (totalDurationProgram < DateTimeUtils.SECONDS_IN_ONE_MINUTE) {
            view_user_programs_list_item_total_duration_exercises.text = String.format(
                Locale.FRANCE,
                context.getString(R.string.fragment_user_programs_list_total_duration_exercises_minutes_text),
                totalDurationProgram
            )
        } else {
            val hours: Int = totalDurationProgram / DateTimeUtils.SECONDS_IN_ONE_MINUTE.toInt()
            val minutes: Int = totalDurationProgram % DateTimeUtils.SECONDS_IN_ONE_MINUTE.toInt()
            view_user_programs_list_item_total_duration_exercises.text = String.format(
                Locale.FRANCE,
                context.getString(R.string.fragment_user_programs_list_total_duration_exercises_hours_text),
                hours.toString(), minutes.toString()
            )
        }

        currentView.setOnClickListener {
            userProgramsListAdapterListener.onProgramClick(programViewDataWrapper.getId())
        }
    }

    private fun calculateTotalDurationProgram(programViewDataWrapper: ProgramViewDataWrapper): Int {
        var totalDuration = 0
        for (exercise in programViewDataWrapper.getExercises()) {
            totalDuration += exercise.durationExercise
        }
        return totalDuration
    }

}
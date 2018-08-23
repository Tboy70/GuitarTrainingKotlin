package thomas.example.com.guitarTrainingKotlin.ui.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_user_programs_list_item.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserProgramsListAdapterListener
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.ProgramObjectWrapper
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.DateTimeUtils
import java.util.*

class ProgramViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView), LayoutContainer {
    override val containerView: View?
        get() = itemView

    private val currentView: View = itemView

    fun bindProgram(programObjectWrapper: ProgramObjectWrapper, userProgramsListAdapterListener: UserProgramsListAdapterListener) {
        view_user_programs_list_item_name.text = programObjectWrapper.program.nameProgram
        view_user_programs_list_item_nb_exercises.text = String.format(
                Locale.FRANCE,
                context.getString(R.string.user_programs_list_nb_exercises_text),
                programObjectWrapper.program.exercises.size.toString()
        )

        val totalDurationProgram: Int = calculateTotalDurationProgram(programObjectWrapper)
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
            userProgramsListAdapterListener.onProgramClick(programObjectWrapper.program.idProgram)
        }
    }

    private fun calculateTotalDurationProgram(programObjectWrapper: ProgramObjectWrapper): Int {
        var totalDuration = 0
        for (exercise in programObjectWrapper.program.exercises) {
            totalDuration += exercise.durationExercise
        }
        return totalDuration
    }

}
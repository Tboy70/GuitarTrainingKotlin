package thomas.guitartrainingkotlin.presentation.utils

import android.app.Activity
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.data.values.ExercisesTypeValues

object ExerciseUtils {

    fun convertTypeExerciseToName(typeExercise: Int, activity: Activity): String {
        return when (typeExercise) {
            ExercisesTypeValues.EXERCISE_SCALE -> activity.getString(R.string.exercise_scale_title_text)
            ExercisesTypeValues.EXERCISE_MODE -> activity.getString(R.string.exercise_mode_title_text)
            ExercisesTypeValues.EXERCISE_PULL_OFF_HAMMER_ON -> activity.getString(R.string.exercise_pull_off_hammer_on_title_text)
            ExercisesTypeValues.EXERCISE_BEND_SLIDE -> activity.getString(R.string.exercise_bend_slide_title_text)
            ExercisesTypeValues.EXERCISE_BACK_FORTH -> activity.getString(R.string.exercise_back_forth_title_text)
            ExercisesTypeValues.EXERCISE_PALM_MUTE -> activity.getString(R.string.exercise_palm_mute_title_text)
            ExercisesTypeValues.EXERCISE_SKIP_STRING -> activity.getString(R.string.exercise_skip_string_title_text)
            ExercisesTypeValues.EXERCISE_TAPPING -> activity.getString(R.string.exercise_tapping_title_text)
            ExercisesTypeValues.EXERCISE_SWEEP_PICKING -> activity.getString(R.string.exercise_sweep_picking_title_text)
            ExercisesTypeValues.EXERCISE_SPEED -> activity.getString(R.string.exercise_speed_title_text)
            ExercisesTypeValues.EXERCISE_SLAP -> activity.getString(R.string.exercise_slap_title_text)
            ExercisesTypeValues.EXERCISE_THREE_FINGERS_RIGHT_HAND -> activity.getString(R.string.exercise_three_fingers_title_text)
            else -> activity.getString(R.string.generic_error_title_text)
        }
    }

    fun convertTypeExerciseToStartIdFragment(typeExercise: Int): Int {
        return when (typeExercise) {
            ExercisesTypeValues.EXERCISE_SCALE -> R.id.launcher_scale_exercise_fragment
            ExercisesTypeValues.EXERCISE_MODE -> R.id.launcher_mode_exercise_fragment
            ExercisesTypeValues.EXERCISE_PULL_OFF_HAMMER_ON -> R.id.launcher_pull_off_hammer_on_exercise_fragment
            ExercisesTypeValues.EXERCISE_BEND_SLIDE -> R.id.launcher_bend_slide_exercise_fragment
            ExercisesTypeValues.EXERCISE_BACK_FORTH -> R.id.launcher_back_forth_exercise_fragment
            ExercisesTypeValues.EXERCISE_PALM_MUTE -> R.id.launcher_palm_mute_exercise_fragment
            ExercisesTypeValues.EXERCISE_SKIP_STRING -> R.id.launcher_skip_string_exercise_fragment
            ExercisesTypeValues.EXERCISE_TAPPING -> R.id.launcher_tapping_exercise_fragment
            ExercisesTypeValues.EXERCISE_SWEEP_PICKING -> R.id.launcher_sweep_picking_exercise_fragment
            ExercisesTypeValues.EXERCISE_SPEED -> R.id.launcher_speed_exercise_fragment
            ExercisesTypeValues.EXERCISE_SLAP -> R.id.launcher_slap_exercise_fragment
            ExercisesTypeValues.EXERCISE_THREE_FINGERS_RIGHT_HAND -> R.id.launcher_three_fingers_exercise_fragment
            else -> R.id.launcher_end_program_fragment
        }
    }

    fun convertTypeExerciseToIdFragment(typeExercise: Int): Int {
        return when (typeExercise) {
            ExercisesTypeValues.EXERCISE_SCALE -> R.id.action_scale_exercise_fragment
            ExercisesTypeValues.EXERCISE_MODE -> R.id.action_mode_exercise_fragment
            ExercisesTypeValues.EXERCISE_PULL_OFF_HAMMER_ON -> R.id.action_pull_off_hammer_on_exercise_fragment
            ExercisesTypeValues.EXERCISE_BEND_SLIDE -> R.id.action_bend_slide_exercise_fragment
            ExercisesTypeValues.EXERCISE_BACK_FORTH -> R.id.action_back_forth_exercise_fragment
            ExercisesTypeValues.EXERCISE_PALM_MUTE -> R.id.action_palm_mute_exercise_fragment
            ExercisesTypeValues.EXERCISE_SKIP_STRING -> R.id.action_skip_string_exercise_fragment
            ExercisesTypeValues.EXERCISE_TAPPING -> R.id.action_tapping_exercise_fragment
            ExercisesTypeValues.EXERCISE_SWEEP_PICKING -> R.id.action_sweep_picking_exercise_fragment
            ExercisesTypeValues.EXERCISE_SPEED -> R.id.action_speed_exercise_fragment
            ExercisesTypeValues.EXERCISE_SLAP -> R.id.action_slap_exercise_fragment
            ExercisesTypeValues.EXERCISE_THREE_FINGERS_RIGHT_HAND -> R.id.action_three_fingers_exercise_fragment
            else -> R.id.action_end_program_fragment
        }
    }

    fun getTypeExerciseIdByName(nameExercise: String, activity: Activity): Int {
        return when (nameExercise) {
            activity.getString(R.string.exercise_scale_title_text) -> ExercisesTypeValues.EXERCISE_SCALE
            activity.getString(R.string.exercise_mode_title_text) -> ExercisesTypeValues.EXERCISE_MODE
            activity.getString(R.string.exercise_pull_off_hammer_on_title_text) -> ExercisesTypeValues.EXERCISE_PULL_OFF_HAMMER_ON
            activity.getString(R.string.exercise_bend_slide_title_text) -> ExercisesTypeValues.EXERCISE_BEND_SLIDE
            activity.getString(R.string.exercise_back_forth_title_text) -> ExercisesTypeValues.EXERCISE_BACK_FORTH
            activity.getString(R.string.exercise_palm_mute_title_text) -> ExercisesTypeValues.EXERCISE_PALM_MUTE
            activity.getString(R.string.exercise_skip_string_title_text) -> ExercisesTypeValues.EXERCISE_SKIP_STRING
            activity.getString(R.string.exercise_tapping_title_text) -> ExercisesTypeValues.EXERCISE_TAPPING
            activity.getString(R.string.exercise_sweep_picking_title_text) -> ExercisesTypeValues.EXERCISE_SWEEP_PICKING
            activity.getString(R.string.exercise_speed_title_text) -> ExercisesTypeValues.EXERCISE_SPEED
            activity.getString(R.string.exercise_slap_title_text) -> ExercisesTypeValues.EXERCISE_SLAP
            activity.getString(R.string.exercise_three_fingers_title_text) -> ExercisesTypeValues.EXERCISE_THREE_FINGERS_RIGHT_HAND
            else -> ExercisesTypeValues.EXERCISE_EMPTY
        }

    }
}
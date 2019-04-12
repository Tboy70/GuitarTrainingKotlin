package thomas.guitartrainingkotlin.presentation.utils

import android.content.Context
import thomas.guitartrainingkotlin.R
import java.lang.Math.abs

object GameUtils {

    private val MAJOR_SCALE_INTERVAL = listOf(2, 4, 5, 7, 9, 11)
    private val MINOR_SCALE_INTERVAL = listOf(2, 3, 5, 7, 8, 10)
    private val PENTATONIC_MINOR_SCALE_INTERVAL = listOf(2, 4, 7, 9)
    private val PENTATONIC_MAJOR_SCALE_INTERVAL = listOf(3, 5, 7, 10)

    fun checkAnswer(
        randomNote: String, randomInterval: String, userAnswer: String, gameMode: Int, context: Context
    ): Boolean {
        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val rightAnswer = if (gameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE) {
            findIntervalValueNormalGame(context, randomInterval, noteArray.indexOf(randomNote))
        } else {
            findIntervalValueReversedGame(context, randomInterval, noteArray.indexOf(randomNote))
        }

        return userAnswer == rightAnswer
    }

    private fun findIntervalValueNormalGame(context: Context, randomInterval: String, indexOfRandomNote: Int): String? {
        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val intervalArray = context.resources.getStringArray(R.array.list_interval)
        return when (randomInterval) {
            intervalArray[0] -> noteArray[(indexOfRandomNote + 0) % ConstValues.NB_NOTES]
            intervalArray[1] -> noteArray[(indexOfRandomNote + 1) % ConstValues.NB_NOTES]
            intervalArray[2] -> noteArray[(indexOfRandomNote + 2) % ConstValues.NB_NOTES]
            intervalArray[3] -> noteArray[(indexOfRandomNote + 3) % ConstValues.NB_NOTES]
            intervalArray[4] -> noteArray[(indexOfRandomNote + 4) % ConstValues.NB_NOTES]
            intervalArray[5] -> noteArray[(indexOfRandomNote + 5) % ConstValues.NB_NOTES]
            intervalArray[6] -> noteArray[(indexOfRandomNote + 6) % ConstValues.NB_NOTES]
            intervalArray[7] -> noteArray[(indexOfRandomNote + 7) % ConstValues.NB_NOTES]
            intervalArray[8] -> noteArray[(indexOfRandomNote + 8) % ConstValues.NB_NOTES]
            intervalArray[9] -> noteArray[(indexOfRandomNote + 9) % ConstValues.NB_NOTES]
            intervalArray[10] -> noteArray[(indexOfRandomNote + 10) % ConstValues.NB_NOTES]
            intervalArray[11] -> noteArray[(indexOfRandomNote + 11) % ConstValues.NB_NOTES]
            intervalArray[12] -> noteArray[(indexOfRandomNote + 12) % ConstValues.NB_NOTES]
            intervalArray[13] -> noteArray[(indexOfRandomNote + 13) % ConstValues.NB_NOTES]
            else -> null
        }
    }

    private fun findIntervalValueReversedGame(
        context: Context,
        randomInterval: String,
        indexOfRandomNote: Int
    ): String? {
        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val intervalArray = context.resources.getStringArray(R.array.list_interval)
        return when (randomInterval) {
            intervalArray[0] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 0)) % ConstValues.NB_NOTES)]
            intervalArray[1] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 1)) % ConstValues.NB_NOTES)]
            intervalArray[2] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 2)) % ConstValues.NB_NOTES)]
            intervalArray[3] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 3)) % ConstValues.NB_NOTES)]
            intervalArray[4] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 4)) % ConstValues.NB_NOTES)]
            intervalArray[5] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 5)) % ConstValues.NB_NOTES)]
            intervalArray[6] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 6)) % ConstValues.NB_NOTES)]
            intervalArray[7] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 7)) % ConstValues.NB_NOTES)]
            intervalArray[8] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 8)) % ConstValues.NB_NOTES)]
            intervalArray[9] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 9)) % ConstValues.NB_NOTES)]
            intervalArray[10] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 10)) % ConstValues.NB_NOTES)]
            intervalArray[11] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 11)) % ConstValues.NB_NOTES)]
            intervalArray[12] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 12)) % ConstValues.NB_NOTES)]
            intervalArray[13] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfRandomNote - 13)) % ConstValues.NB_NOTES)]
            else -> null
        }
    }

    fun getScaleInterval(context: Context, randomScale: String): String {
        val scaleArray = context.resources.getStringArray(R.array.list_scales)
        return when (randomScale) {
            scaleArray[0] -> " 1T ; 1T ; 0.5T ; 1T ; 1T ; 1T ; 0.5T "
            scaleArray[1] -> " 1T ; 0.5T ; 1T ; 1T ; 0.5T ; 1T ;1T "
            scaleArray[2] -> " 1T ; 1T ; 1.5T ; 1T ; 1.5T "
            scaleArray[3] -> " 1.5T ; 1T ; 1T ; 1.5T ; 1T "
            else -> ""
        }
    }

    fun checkScaleGameAnswer(
        answersList: List<String>,
        randomScale: String,
        randomNote: String,
        context: Context
    ): List<Boolean> {
        val results = mutableListOf<Boolean>()

        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val indexNote = noteArray.indexOf(randomNote)

        val expectedResultList = mutableListOf(randomNote)

        val scaleArray = context.resources.getStringArray(R.array.list_scales)
        val scaleInterval = when (randomScale) {
            scaleArray[0] -> MAJOR_SCALE_INTERVAL
            scaleArray[1] -> MINOR_SCALE_INTERVAL
            scaleArray[2] -> PENTATONIC_MINOR_SCALE_INTERVAL
            scaleArray[3] -> PENTATONIC_MAJOR_SCALE_INTERVAL
            else -> MAJOR_SCALE_INTERVAL    // TODO ?
        }

        scaleInterval.forEachIndexed { index, value ->
            expectedResultList.add(
                noteArray[(indexNote + value) % ConstValues.NB_NOTES]
            )
        }

        if (expectedResultList.size == answersList.size) {
            expectedResultList.forEachIndexed { index, value ->
                results.add(value == answersList[index])
            }
        }

        return results
    }
}
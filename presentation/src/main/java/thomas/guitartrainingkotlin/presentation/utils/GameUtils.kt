package thomas.guitartrainingkotlin.presentation.utils

import android.content.Context
import thomas.guitartrainingkotlin.R
import java.lang.Math.abs

object GameUtils {

    private val MAJOR_SCALE_INTERVAL = listOf(2, 4, 5, 7, 9, 11, 12)
    private val MINOR_SCALE_INTERVAL = listOf(2, 3, 5, 7, 8, 10, 12)
    private val PENTATONIC_MINOR_SCALE_INTERVAL = listOf(2, 4, 7, 9, 12)
    private val PENTATONIC_MAJOR_SCALE_INTERVAL = listOf(3, 5, 7, 10, 12)
    private val BLUES_SCALE = listOf(3, 5, 6, 7, 10, 12)

    fun checkIntervalGameAnswer(
        givenNote: String, givenInterval: String, userAnswer: String, gameMode: Int, context: Context
    ): Boolean {
        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val expectedAnswer = if (gameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE) {
            findIntervalValueNormalGame(context, givenInterval, noteArray.indexOf(givenNote))
        } else {
            findIntervalValueReversedGame(context, givenInterval, noteArray.indexOf(givenNote))
        }

        return userAnswer == expectedAnswer
    }

    private fun findIntervalValueNormalGame(context: Context, givenInterval: String, indexOfGivenNote: Int): String? {
        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val intervalArray = context.resources.getStringArray(R.array.list_interval)
        return when (givenInterval) {
            intervalArray[0] -> noteArray[(indexOfGivenNote + 0) % ConstValues.NB_NOTES]
            intervalArray[1] -> noteArray[(indexOfGivenNote + 1) % ConstValues.NB_NOTES]
            intervalArray[2] -> noteArray[(indexOfGivenNote + 2) % ConstValues.NB_NOTES]
            intervalArray[3] -> noteArray[(indexOfGivenNote + 3) % ConstValues.NB_NOTES]
            intervalArray[4] -> noteArray[(indexOfGivenNote + 4) % ConstValues.NB_NOTES]
            intervalArray[5] -> noteArray[(indexOfGivenNote + 5) % ConstValues.NB_NOTES]
            intervalArray[6] -> noteArray[(indexOfGivenNote + 6) % ConstValues.NB_NOTES]
            intervalArray[7] -> noteArray[(indexOfGivenNote + 7) % ConstValues.NB_NOTES]
            intervalArray[8] -> noteArray[(indexOfGivenNote + 8) % ConstValues.NB_NOTES]
            intervalArray[9] -> noteArray[(indexOfGivenNote + 9) % ConstValues.NB_NOTES]
            intervalArray[10] -> noteArray[(indexOfGivenNote + 10) % ConstValues.NB_NOTES]
            intervalArray[11] -> noteArray[(indexOfGivenNote + 11) % ConstValues.NB_NOTES]
            intervalArray[12] -> noteArray[(indexOfGivenNote + 12) % ConstValues.NB_NOTES]
            else -> null
        }
    }

    private fun findIntervalValueReversedGame(
        context: Context,
        givenInterval: String,
        indexOfGivenNote: Int
    ): String? {
        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val intervalArray = context.resources.getStringArray(R.array.list_interval)
        return when (givenInterval) {
            intervalArray[0] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 0)) % ConstValues.NB_NOTES)]
            intervalArray[1] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 1)) % ConstValues.NB_NOTES)]
            intervalArray[2] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 2)) % ConstValues.NB_NOTES)]
            intervalArray[3] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 3)) % ConstValues.NB_NOTES)]
            intervalArray[4] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 4)) % ConstValues.NB_NOTES)]
            intervalArray[5] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 5)) % ConstValues.NB_NOTES)]
            intervalArray[6] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 6)) % ConstValues.NB_NOTES)]
            intervalArray[7] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 7)) % ConstValues.NB_NOTES)]
            intervalArray[8] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 8)) % ConstValues.NB_NOTES)]
            intervalArray[9] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 9)) % ConstValues.NB_NOTES)]
            intervalArray[10] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 10)) % ConstValues.NB_NOTES)]
            intervalArray[11] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 11)) % ConstValues.NB_NOTES)]
            intervalArray[12] -> noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 12)) % ConstValues.NB_NOTES)]
            else -> null
        }
    }

    fun checkReversedIntervalGameAnswer(givenInterval: String, userAnswer: String, context: Context): Boolean {
        val intervalArray = context.resources.getStringArray(R.array.list_interval)
        val expectedResult = when (givenInterval) {
            intervalArray[0] -> intervalArray[12]
            intervalArray[1] -> intervalArray[11]
            intervalArray[2] -> intervalArray[10]
            intervalArray[3] -> intervalArray[9]
            intervalArray[4] -> intervalArray[8]
            intervalArray[5] -> intervalArray[7]
            intervalArray[6] -> intervalArray[6]
            intervalArray[7] -> intervalArray[5]
            intervalArray[8] -> intervalArray[4]
            intervalArray[9] -> intervalArray[3]
            intervalArray[10] -> intervalArray[2]
            intervalArray[11] -> intervalArray[1]
            intervalArray[12] -> intervalArray[0]
            else -> intervalArray[0]
        }

        return expectedResult == userAnswer
    }

    fun checkScaleGameAnswer(
        answersList: List<String>,
        givenScale: String,
        givenNote: String,
        context: Context
    ): List<Boolean> {
        val resultList = mutableListOf<Boolean>()

        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val indexOfGivenNote = noteArray.indexOf(givenNote)

        val expectedResultList = mutableListOf(givenNote)

        val scaleArray = context.resources.getStringArray(R.array.list_scales)
        val scaleInterval = when (givenScale) {
            scaleArray[0] -> BLUES_SCALE
            scaleArray[1] -> MAJOR_SCALE_INTERVAL
            scaleArray[2] -> MINOR_SCALE_INTERVAL
            scaleArray[3] -> PENTATONIC_MAJOR_SCALE_INTERVAL
            scaleArray[4] -> PENTATONIC_MINOR_SCALE_INTERVAL
            else -> MAJOR_SCALE_INTERVAL
        }

        scaleInterval.forEachIndexed { _, value ->
            expectedResultList.add(
                noteArray[(indexOfGivenNote + value) % ConstValues.NB_NOTES]
            )
        }

        if (expectedResultList.size == answersList.size) {
            expectedResultList.forEachIndexed { index, value ->
                resultList.add(value == answersList[index])
            }
        }

        return resultList
    }

    fun getScaleIntervalHelp(context: Context, givenScale: String): String {
        val scaleArray = context.resources.getStringArray(R.array.list_scales)
        return when (givenScale) {
            scaleArray[0] -> context.getString(R.string.blues_scale_interval)
            scaleArray[1] -> context.getString(R.string.major_scale_interval)
            scaleArray[2] -> context.getString(R.string.minor_scale_interval)
            scaleArray[3] -> context.getString(R.string.pentatonic_major_scale_interval)
            scaleArray[4] -> context.getString(R.string.pentatonic_minor_scale_interval)
            else -> ""
        }
    }
}
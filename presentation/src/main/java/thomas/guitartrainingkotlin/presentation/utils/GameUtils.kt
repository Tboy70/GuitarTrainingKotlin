package thomas.guitartrainingkotlin.presentation.utils

import android.content.Context
import thomas.guitartrainingkotlin.R
import java.lang.Math.abs
import java.util.*

object GameUtils {

    private val MAJOR_SCALE_INTERVAL = listOf(2, 4, 5, 7, 9, 11, 12)
    private val MINOR_SCALE_INTERVAL = listOf(2, 3, 5, 7, 8, 10, 12)
    private val MAJOR_BLUES_SCALE_INTERVAL = listOf(2, 3, 4, 7, 9, 12)
    private val MINOR_BLUES_SCALE_INTERVAL = listOf(3, 5, 6, 7, 10, 12)
    private val PENTATONIC_MAJOR_SCALE_INTERVAL = listOf(2, 4, 7, 9, 12)
    private val PENTATONIC_MINOR_SCALE_INTERVAL = listOf(3, 5, 7, 10, 12)

    fun checkIntervalGameAnswer(
        givenNote: String, givenInterval: String, userAnswer: String, intervalGameMode: Int, context: Context
    ): Boolean {
        val noteArray = context.resources.getStringArray(R.array.list_notes)
        return userAnswer == findIntervalValue(context, givenInterval, noteArray.indexOf(givenNote), intervalGameMode)
    }

    private fun findIntervalValue(
        context: Context,
        givenInterval: String,
        indexOfGivenNote: Int,
        intervalGameMode: Int
    ): String? {
        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val intervalArray = context.resources.getStringArray(R.array.list_interval)
        return when (givenInterval) {
            intervalArray[0] ->
                if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                    noteArray[(indexOfGivenNote + 0) % ConstValues.NB_NOTES]
                else
                    noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 0)) % ConstValues.NB_NOTES)]
            intervalArray[1] ->
                if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                    noteArray[(indexOfGivenNote + 1) % ConstValues.NB_NOTES]
                else
                    noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 1)) % ConstValues.NB_NOTES)]
            intervalArray[2] ->
                if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                    noteArray[(indexOfGivenNote + 2) % ConstValues.NB_NOTES]
                else
                    noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 2)) % ConstValues.NB_NOTES)]
            intervalArray[3] ->
                if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                    noteArray[(indexOfGivenNote + 3) % ConstValues.NB_NOTES]
                else
                    noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 3)) % ConstValues.NB_NOTES)]
            intervalArray[4] ->
                if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                    noteArray[(indexOfGivenNote + 4) % ConstValues.NB_NOTES]
                else
                    noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 4)) % ConstValues.NB_NOTES)]
            intervalArray[5] ->
                if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                    noteArray[(indexOfGivenNote + 5) % ConstValues.NB_NOTES]
                else
                    noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 5)) % ConstValues.NB_NOTES)]
            intervalArray[6] ->
                if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                    noteArray[(indexOfGivenNote + 6) % ConstValues.NB_NOTES]
                else
                    noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 6)) % ConstValues.NB_NOTES)]
            intervalArray[7] ->
                if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                    noteArray[(indexOfGivenNote + 7) % ConstValues.NB_NOTES]
                else
                    noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 7)) % ConstValues.NB_NOTES)]
            intervalArray[8] ->
                if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                    noteArray[(indexOfGivenNote + 8) % ConstValues.NB_NOTES]
                else
                    noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 8)) % ConstValues.NB_NOTES)]
            intervalArray[9] ->
                if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                    noteArray[(indexOfGivenNote + 9) % ConstValues.NB_NOTES]
                else
                    noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 9)) % ConstValues.NB_NOTES)]
            intervalArray[10] ->
                if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                    noteArray[(indexOfGivenNote + 10) % ConstValues.NB_NOTES]
                else
                    noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 10)) % ConstValues.NB_NOTES)]
            intervalArray[11] ->
                if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                    noteArray[(indexOfGivenNote + 11) % ConstValues.NB_NOTES]
                else
                    noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 11)) % ConstValues.NB_NOTES)]
            intervalArray[12] ->
                if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                    noteArray[(indexOfGivenNote + 12) % ConstValues.NB_NOTES]
                else
                    noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - 12)) % ConstValues.NB_NOTES)]
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
            scaleArray[0] -> MAJOR_SCALE_INTERVAL
            scaleArray[1] -> MINOR_SCALE_INTERVAL
            scaleArray[2] -> MAJOR_BLUES_SCALE_INTERVAL
            scaleArray[3] -> MINOR_BLUES_SCALE_INTERVAL
            scaleArray[4] -> PENTATONIC_MAJOR_SCALE_INTERVAL
            scaleArray[5] -> PENTATONIC_MINOR_SCALE_INTERVAL
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
            scaleArray[0] -> context.getString(R.string.major_scale_interval)
            scaleArray[1] -> context.getString(R.string.minor_scale_interval)
            scaleArray[2] -> context.getString(R.string.major_blues_scale_interval)
            scaleArray[3] -> context.getString(R.string.minor_blues_scale_interval)
            scaleArray[4] -> context.getString(R.string.pentatonic_major_scale_interval)
            scaleArray[4] -> context.getString(R.string.pentatonic_minor_scale_interval)
            else -> ""
        }
    }

    fun generateCorrectScale(givenNote: String, context: Context): Pair<MutableList<String>, String> {
        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val scaleArray = context.resources.getStringArray(R.array.list_scales)

        val indexOfGivenNote = noteArray.indexOf(givenNote)

        val generatedScale = mutableListOf(givenNote)

        val scaleToGenerated = when (Random().nextInt(ConstValues.NB_SCALES)) {
            1 -> Pair(MAJOR_SCALE_INTERVAL, scaleArray[0])
            2 -> Pair(MINOR_SCALE_INTERVAL, scaleArray[1])
            3 -> Pair(MAJOR_BLUES_SCALE_INTERVAL, scaleArray[2])
            4 -> Pair(MINOR_BLUES_SCALE_INTERVAL, scaleArray[3])
            5 -> Pair(PENTATONIC_MAJOR_SCALE_INTERVAL, scaleArray[4])
            6 -> Pair(PENTATONIC_MINOR_SCALE_INTERVAL, scaleArray[5])
            else -> Pair(MAJOR_SCALE_INTERVAL, scaleArray[0])
        }

        scaleToGenerated.first.forEachIndexed { _, value ->
            generatedScale.add(
                noteArray[(indexOfGivenNote + value) % ConstValues.NB_NOTES]
            )
        }

        return Pair(generatedScale, scaleToGenerated.second)
    }

    // TODO : Finish
    fun generateRandomScale(givenNote: String, context: Context): List<String> {
        val correctOrRandomScale = Random().nextInt(2)
        if (correctOrRandomScale == 0) {
            generateCorrectScale(givenNote, context)
        } else {
            generateCorrectScale(givenNote, context)
        }
        return generateCorrectScale(givenNote, context).first   // TODO : WRONG !
    }
}
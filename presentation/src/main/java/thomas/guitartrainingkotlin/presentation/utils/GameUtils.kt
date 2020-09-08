package thomas.guitartrainingkotlin.presentation.utils

import android.content.Context
import android.util.Log
import thomas.guitartrainingkotlin.R
import java.util.*
import kotlin.math.abs

object GameUtils {

    private val MAJOR_SCALE_INTERVAL = listOf(2, 4, 5, 7, 9, 11, 12)
    private val MINOR_NATURAL_SCALE_INTERVAL = listOf(2, 3, 5, 7, 8, 10, 12)
    private val MINOR_MELODIC_SCALE_INTERVAL = listOf(2, 3, 5, 7, 9, 11, 12)
    private val MINOR_HARMONIC_SCALE_INTERVAL = listOf(2, 3, 5, 7, 8, 11, 12)
    private val PENTATONIC_MAJOR_SCALE_INTERVAL = listOf(2, 4, 7, 9, 12)
    private val PENTATONIC_MINOR_SCALE_INTERVAL = listOf(3, 5, 7, 10, 12)
    private val MAJOR_BLUES_SCALE_INTERVAL = listOf(2, 3, 4, 7, 9, 12)
    private val MINOR_BLUES_SCALE_INTERVAL = listOf(3, 5, 6, 7, 10, 12)

    fun checkIntervalGameAnswer(
        givenNote: String,
        givenInterval: String,
        userAnswer: String,
        intervalGameMode: Int,
        context: Context
    ): Boolean {
        val noteArray = context.resources.getStringArray(R.array.list_notes)
        return userAnswer == findIntervalValue(
            context,
            givenInterval,
            noteArray.indexOf(givenNote),
            intervalGameMode
        )
    }

    private fun findIntervalValue(
        context: Context, givenInterval: String, indexOfGivenNote: Int, intervalGameMode: Int
    ): String? {
        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val intervalArray = context.resources.getStringArray(R.array.list_interval)

        intervalArray.indexOf(givenInterval).let { index ->
            return if (intervalGameMode == ConstValues.INTERVAL_NORMAL_GAME_MODE)
                noteArray[(indexOfGivenNote + index) % ConstValues.NB_NOTES]
            else
                noteArray[abs((ConstValues.NB_NOTES + (indexOfGivenNote - index)) % ConstValues.NB_NOTES)]
        }
    }

    fun checkReversedIntervalGameAnswer(
        givenInterval: String,
        userAnswer: String,
        context: Context
    ): Boolean? {
        val intervalArray = context.resources.getStringArray(R.array.list_interval)
        intervalArray.indexOf(givenInterval).let { index ->
            return intervalArray[index + ConstValues.NB_NOTES - (index * 2)] == userAnswer
        }
    }

    fun checkScaleGameAnswer(
        answersList: List<String>, scale: String, referenceNote: String, context: Context
    ): List<Boolean> {
        val resultList = mutableListOf<Boolean>()

        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val indexOfGivenNote = noteArray.indexOf(referenceNote)

        val expectedResultList = mutableListOf(referenceNote)

        val scaleArray = context.resources.getStringArray(R.array.list_scales)
        val scaleInterval = when (scale) {
            scaleArray[0] -> MAJOR_SCALE_INTERVAL
            scaleArray[1] -> MINOR_NATURAL_SCALE_INTERVAL
            scaleArray[2] -> MINOR_HARMONIC_SCALE_INTERVAL
            scaleArray[3] -> MINOR_MELODIC_SCALE_INTERVAL
            scaleArray[4] -> PENTATONIC_MAJOR_SCALE_INTERVAL
            scaleArray[5] -> PENTATONIC_MINOR_SCALE_INTERVAL
            scaleArray[6] -> MAJOR_BLUES_SCALE_INTERVAL
            scaleArray[7] -> MINOR_BLUES_SCALE_INTERVAL
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

    fun retrieveScaleIntervalHelp(context: Context, givenScale: String): String {
        val scaleArray = context.resources.getStringArray(R.array.list_scales)
        return when (givenScale) {
            scaleArray[0] -> context.getString(R.string.major_scale_interval)
            scaleArray[1] -> context.getString(R.string.minor_natural_scale_interval)
            scaleArray[2] -> context.getString(R.string.minor_harmonic_scale_interval)
            scaleArray[3] -> context.getString(R.string.minor_melodic_scale_interval)
            scaleArray[4] -> context.getString(R.string.pentatonic_major_scale_interval)
            scaleArray[5] -> context.getString(R.string.pentatonic_minor_scale_interval)
            scaleArray[6] -> context.getString(R.string.major_blues_scale_interval)
            scaleArray[7] -> context.getString(R.string.minor_blues_scale_interval)
            else -> ""
        }
    }

    fun generateCorrectScale(
        referenceNote: String,
        scale: String?,
        context: Context
    ): Pair<MutableList<String>, String> {
        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val indexOfGivenNote = noteArray.indexOf(referenceNote)
        val generatedScale = mutableListOf(referenceNote)
        val scaleToGenerate =
            scale?.let { scaleName ->
                getCorrectScale(context, scaleName)
            } ?: getRandomScale(context)

        scaleToGenerate.first.forEachIndexed { _, value ->
            generatedScale.add(
                noteArray[(indexOfGivenNote + value) % ConstValues.NB_NOTES]
            )
        }

        Log.e("TEST", "Gamme générée : $generatedScale")
        Log.e("TEST", "Gamme générée taille : " + generatedScale.size)
        return Pair(generatedScale, scaleToGenerate.second)
    }

    fun generateIncorrectScale(
        givenNote: String,
        scale: String,
        context: Context
    ): Pair<MutableList<String>, String> {
        val noteArray = context.resources.getStringArray(R.array.list_notes)
        val indexOfGivenNote = noteArray.indexOf(givenNote)
        var generatedScale = mutableListOf(givenNote)
        val scaleToGenerate = getRandomScale(context, scale)

        scaleToGenerate.first.forEachIndexed { _, _ ->
            generatedScale.add(
                noteArray[Random().nextInt(ConstValues.NB_NOTES)]
            )
        }

        generatedScale = generatedScale.drop(1).toMutableList()
        generatedScale.add(0, noteArray[(indexOfGivenNote + 0) % ConstValues.NB_NOTES])
        generatedScale = generatedScale.dropLast(1).toMutableList()
        generatedScale.add(noteArray[(indexOfGivenNote + 0) % ConstValues.NB_NOTES])

        Log.e("TEST", "Gamme générée : " + generatedScale)
        Log.e("TEST", "Gamme générée taille : " + generatedScale.size)
        return Pair(generatedScale, scaleToGenerate.second)
    }

    /**
     * Returns pair :
     *  -> First : Intervals between each note
     *  -> Second : Scale name
     */
    private fun getRandomScale(context: Context, scale: String?): Pair<List<Int>, String> {

        Log.e("TEST", "Gamme à générer : $scale")

        val scaleArray = context.resources.getStringArray(R.array.list_scales)

        if (scale == context.getString(R.string.tone_pentatonic_minor) ||
            scale == context.getString(R.string.tone_pentatonic_minor)
        ) {
            return when (Random().nextInt(ConstValues.NB_SCALES)) {
                0 -> Pair(PENTATONIC_MAJOR_SCALE_INTERVAL, scaleArray[4])
                1 -> Pair(PENTATONIC_MINOR_SCALE_INTERVAL, scaleArray[5])
                2 -> Pair(PENTATONIC_MAJOR_SCALE_INTERVAL, scaleArray[4])
                3 -> Pair(PENTATONIC_MINOR_SCALE_INTERVAL, scaleArray[5])
                4 -> Pair(PENTATONIC_MAJOR_SCALE_INTERVAL, scaleArray[4])
                5 -> Pair(PENTATONIC_MINOR_SCALE_INTERVAL, scaleArray[5])
                6 -> Pair(PENTATONIC_MAJOR_SCALE_INTERVAL, scaleArray[4])
                7 -> Pair(PENTATONIC_MINOR_SCALE_INTERVAL, scaleArray[5])
                else -> Pair(PENTATONIC_MINOR_SCALE_INTERVAL, scaleArray[5])
            }
        } else {
            return when (Random().nextInt(ConstValues.NB_SCALES)) {
                0 -> Pair(MAJOR_SCALE_INTERVAL, scaleArray[0])
                1 -> Pair(MINOR_NATURAL_SCALE_INTERVAL, scaleArray[1])
                2 -> Pair(MINOR_HARMONIC_SCALE_INTERVAL, scaleArray[2])
                3 -> Pair(MINOR_MELODIC_SCALE_INTERVAL, scaleArray[3])
                4 -> Pair(MAJOR_SCALE_INTERVAL, scaleArray[0])
                5 -> Pair(MINOR_NATURAL_SCALE_INTERVAL, scaleArray[1])
                6 -> Pair(MAJOR_BLUES_SCALE_INTERVAL, scaleArray[6])
                7 -> Pair(MINOR_BLUES_SCALE_INTERVAL, scaleArray[7])
                else -> Pair(MAJOR_SCALE_INTERVAL, scaleArray[0])
            }
        }
    }

    private fun getRandomScale(context: Context): Pair<List<Int>, String> {
        val scaleArray = context.resources.getStringArray(R.array.list_scales)
        return when (Random().nextInt(ConstValues.NB_SCALES)) {
            0 -> Pair(MAJOR_SCALE_INTERVAL, scaleArray[0])
            1 -> Pair(MINOR_NATURAL_SCALE_INTERVAL, scaleArray[1])
            2 -> Pair(MINOR_HARMONIC_SCALE_INTERVAL, scaleArray[2])
            3 -> Pair(MINOR_MELODIC_SCALE_INTERVAL, scaleArray[3])
            4 -> Pair(PENTATONIC_MAJOR_SCALE_INTERVAL, scaleArray[4])
            5 -> Pair(PENTATONIC_MINOR_SCALE_INTERVAL, scaleArray[5])
            6 -> Pair(MAJOR_BLUES_SCALE_INTERVAL, scaleArray[6])
            7 -> Pair(MINOR_BLUES_SCALE_INTERVAL, scaleArray[7])
            else -> Pair(MAJOR_SCALE_INTERVAL, scaleArray[0])
        }
    }

    /**
     * Returns pair :
     *  -> First : Intervals between each note
     *  -> Second : Scale name
     */
    private fun getCorrectScale(context: Context, scale: String): Pair<List<Int>, String> {

        Log.e("TEST", "Nom de la gamme à générer : $scale")

        val scaleArray = context.resources.getStringArray(R.array.list_scales)
        return when (scale) {
            context.getString(R.string.tone_major) -> Pair(MAJOR_SCALE_INTERVAL, scaleArray[0])
            context.getString(R.string.tone_minor_natural) -> Pair(
                MINOR_NATURAL_SCALE_INTERVAL,
                scaleArray[1]
            )
            context.getString(R.string.tone_minor_harmonic) -> Pair(
                MINOR_HARMONIC_SCALE_INTERVAL,
                scaleArray[2]
            )
            context.getString(R.string.tone_minor_melodic) -> Pair(
                MINOR_MELODIC_SCALE_INTERVAL,
                scaleArray[3]
            )
            context.getString(R.string.tone_pentatonic_major) -> Pair(
                PENTATONIC_MAJOR_SCALE_INTERVAL,
                scaleArray[4]
            )
            context.getString(R.string.tone_pentatonic_minor) -> Pair(
                PENTATONIC_MINOR_SCALE_INTERVAL,
                scaleArray[5]
            )
            context.getString(R.string.tone_blues_major) -> Pair(
                MAJOR_BLUES_SCALE_INTERVAL,
                scaleArray[6]
            )
            context.getString(R.string.tone_blues_minor) -> Pair(
                MINOR_BLUES_SCALE_INTERVAL,
                scaleArray[7]
            )
            else -> Pair(MAJOR_SCALE_INTERVAL, scaleArray[0])
        }
    }
}
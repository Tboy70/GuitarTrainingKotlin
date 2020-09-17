package thomas.guitartrainingkotlin.presentation.utils

import android.content.Context
import android.util.Log
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.viewmodel.game.IntervalGameViewModel
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

    private val NB_TONS_WITH_ALTERATION =
        listOf(0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5)

    // Map --> index to list(NbNoteToPass, NbTons)
    private val INTERVAL_TO_TONS = mapOf(
        0 to listOf(0, 0),
        1 to listOf(1, 0.5),
        2 to listOf(1, 1),
        3 to listOf(2, 1.5),
        4 to listOf(2, 2),
        5 to listOf(3, 2),
        6 to listOf(3, 2.5),
        7 to listOf(3, 3),
        8 to listOf(4, 3),
        9 to listOf(4, 3.5),
        10 to listOf(4, 4),
        11 to listOf(5, 4),
        12 to listOf(5, 4.5),
        13 to listOf(6, 5),
        14 to listOf(6, 5.5),
        15 to listOf(7, 0)
    )

    // Map --> To handle same note from the array-list (example : D# / Eb)
    private val NOTE_WITH_ALTERATION_MAP_INDEX = mapOf(
        0 to 0,
        1 to 1,
        2 to 1,
        3 to 2,
        4 to 3,
        5 to 3,
        6 to 4,
        7 to 5,
        8 to 6,
        9 to 6,
        10 to 7,
        11 to 8,
        12 to 8,
        13 to 9,
        14 to 10,
        15 to 10,
        16 to 11
    )

    private const val FLAT_SYMBOL = "b"
    private const val SHARP_SYMBOL = "#"

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

        val noteArray = context.resources.getStringArray(R.array.list_notes_with_alterations)
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
        val noteArray = context.resources.getStringArray(R.array.list_notes_with_alterations)
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
        val noteArray = context.resources.getStringArray(R.array.list_notes_with_alterations)
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


    /**************************************************************************/
    /************************* REFACTOR INTERVAL GAME *************************/
    /**************************************************************************/

    fun computeCorrectNote(
        context: Context,
        gameMode: Int,
        startNote: Int,
        interval: Int
    ): String {
        val intervalValue = context.resources.getStringArray(R.array.list_interval)[interval]
        val intervalIndex = context.resources.getStringArray(R.array.list_interval).indexOf(intervalValue)

        // Compute the exact number of tones for the given interval.
        val exactTonesForInterval = INTERVAL_TO_TONS[intervalIndex]?.get(1).toString().toFloat()

        val startNoteValue = context.resources.getStringArray(R.array.list_notes_with_alterations)[startNote]

        if (intervalValue == context.resources.getString(R.string.interval_octave) ||
            intervalValue == context.resources.getString(R.string.interval_unisson)
        ) {
            return startNoteValue
        }

        // Compute the note to reach (without take account of the alterations)
        return computeNoteToReach(context, gameMode, startNoteValue, intervalValue).let { noteToReach ->
            computeCorrectAnswerNote(
                gameMode,
                noteToReach,
                computeTonesBetweenTwoNotes(context, gameMode, startNoteValue, noteToReach),
                exactTonesForInterval
            )
        }
    }

    /**
     * Compute the note to reach, given the begin note and the interval
     * Computation without alteration !
     */
    private fun computeNoteToReach(
        context: Context,
        randomGame: Int,
        startNoteValue: String,
        intervalValue: String
    ): String {

        val startNoteIndexWithoutAlteration =
            context.resources.getStringArray(R.array.list_notes_without_alteration)
                .indexOf(startNoteValue.substring(0, 1))

        val intervalIndex =
            context.resources.getStringArray(R.array.list_interval).indexOf(intervalValue)

        val nbNotesToPassGivenInterval = INTERVAL_TO_TONS[intervalIndex]?.get(0).toString().toInt()

        val endNoteIndex = if (randomGame == IntervalGameViewModel.GAME_FIND_NOTE_GIVEN_INTERVAL) {
            abs(ConstValues.NB_NOTES_WITHOUT_ALTERATION + (startNoteIndexWithoutAlteration + nbNotesToPassGivenInterval)) % ConstValues.NB_NOTES_WITHOUT_ALTERATION
        } else {
            abs(ConstValues.NB_NOTES_WITHOUT_ALTERATION + (startNoteIndexWithoutAlteration - nbNotesToPassGivenInterval)) % ConstValues.NB_NOTES_WITHOUT_ALTERATION
        }

        return context.resources.getStringArray(R.array.list_notes_without_alteration)[endNoteIndex]
    }

    /**
     * Compute the number of tones between two notes.
     */
    private fun computeTonesBetweenTwoNotes(
        context: Context,
        randomGame: Int,
        startNoteValue: String,
        endNoteValue: String
    ): Double {

        val startNoteIndexWithAlteration =
            context.resources.getStringArray(R.array.list_notes_with_alterations)
                .indexOf(startNoteValue)

        val endNoteIndexWithAlteration =
            context.resources.getStringArray(R.array.list_notes_with_alterations)
                .indexOf(endNoteValue)

        // To take account the note which are the same. Example -> C# / Db
        val rightStartNoteIndexWithAlteration =
            NOTE_WITH_ALTERATION_MAP_INDEX[startNoteIndexWithAlteration]
        val rightEndNoteIndexWithAlteration =
            NOTE_WITH_ALTERATION_MAP_INDEX[endNoteIndexWithAlteration]

        var tonsBetweenNotes = 0.0

        rightStartNoteIndexWithAlteration?.let { startNoteIndex ->
            rightEndNoteIndexWithAlteration?.let { endNoteIndex ->

                var diff: Int

                if (randomGame == IntervalGameViewModel.GAME_FIND_NOTE_GIVEN_INTERVAL) {
                    diff = abs(startNoteIndex - endNoteIndex)
                    if (startNoteIndex > endNoteIndex) {
                        diff = ConstValues.NB_NOTES_MIXING_SAME_NOTE - diff
                    }
                } else {
                    diff =
                        abs((ConstValues.NB_NOTES_MIXING_SAME_NOTE + startNoteIndex - endNoteIndex) % ConstValues.NB_NOTES_MIXING_SAME_NOTE)
                }

                for (i in 0 until diff) {
                    val rightValue =
                        NOTE_WITH_ALTERATION_MAP_INDEX[(startNoteIndexWithAlteration + i) % ConstValues.NB_NOTES_MIXING_SAME_NOTE]
                    tonsBetweenNotes += NB_TONS_WITH_ALTERATION[rightValue!!]
                }
            }
        }

        return tonsBetweenNotes
    }

    /**
     * Compute the correct answer.
     */
    private fun computeCorrectAnswerNote(
        gameMode: Int,
        noteToReach: String,
        tonesBetweenStartAndEndNote: Double,
        exactTonesForInterval: Float
    ): String {

        var goodAnswer = noteToReach

        // Difference of tones between what we computed and what we are expecting
        val toneDifference = exactTonesForInterval - tonesBetweenStartAndEndNote

        val semiToneDifference = abs(toneDifference / 0.5).toInt()

        for (i in 0 until semiToneDifference) {
            if (toneDifference < 0) {
                if (gameMode == IntervalGameViewModel.GAME_FIND_NOTE_GIVEN_INTERVAL) {
                    goodAnswer += FLAT_SYMBOL
                } else if (gameMode == IntervalGameViewModel.GAME_FIND_NOTE_GIVEN_INTERVAL_REVERSED) {
                    goodAnswer += SHARP_SYMBOL
                }
            } else if (toneDifference > 0) {
                if (gameMode == IntervalGameViewModel.GAME_FIND_NOTE_GIVEN_INTERVAL) {
                    goodAnswer += SHARP_SYMBOL
                } else if (gameMode == IntervalGameViewModel.GAME_FIND_NOTE_GIVEN_INTERVAL_REVERSED) {
                    goodAnswer += FLAT_SYMBOL
                }
            }
        }

        return goodAnswer
    }

    fun computeRightInterval(context: Context, gameMode: Int, startNote: Int, interval: Int): String {
        return computeCorrectNote(context, gameMode, startNote, interval)
    }

    fun computeFalseAnswers(context: Context, interval: Int): String {
        return context.resources.getStringArray(R.array.list_interval)[interval]
    }

    /***********************************************************************************/
    /************************* REFACTOR REVERSED INTERVAL GAME *************************/
    /***********************************************************************************/

    fun computeReversedInterval(context: Context, beginIntervalIndex: Int): Pair<String, String> {
        val beginIntervalValue = context.resources.getStringArray(R.array.list_interval)[beginIntervalIndex]
        val reversedIntervalIndex = beginIntervalIndex + ConstValues.NB_INTERVAL - (beginIntervalIndex * 2) - 1
        val reversedIntervalValue = context.resources.getStringArray(R.array.list_interval)[reversedIntervalIndex]

        return Pair(beginIntervalValue, reversedIntervalValue)
    }
}
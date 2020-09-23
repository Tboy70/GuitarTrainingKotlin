package thomas.guitartrainingkotlin.presentation.utils

import android.content.Context
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.domain.model.game.Note
import thomas.guitartrainingkotlin.domain.model.game.Scale
import kotlin.math.abs
import kotlin.random.Random

object GameUtils2 {

    private const val NB_ALL_NOTES = 35
    private const val FLAT_SYMBOL = "b"
    private const val SHARP_SYMBOL = "#"
    private const val SEMITONE_VALUE = 0.5
    private const val NB_NOTES_MIXING_SAME_NOTE = 12
    private const val NB_NOTES_WITHOUT_ALTERATION = 7

    private var listScale: Array<out String> = arrayOf()
    private var listNoteWithAlteration: Array<out String> = arrayOf()
    private var listNoteWithoutAlteration: Array<out String> = arrayOf()

    // Map index to equal notes --> C# / Db for example
    private val NOTE_INDEX_TO_EQUAL_NOTES = mapOf(
        0 to listOf(0), // C
        1 to listOf(1, 2), // C# / Db
        2 to listOf(3), // D
        3 to listOf(4, 5), // D# / Eb
        4 to listOf(6), // E
        5 to listOf(7), // F
        6 to listOf(8, 9), // F# / Gb
        7 to listOf(10), // G
        8 to listOf(11, 12), // G# / Ab
        9 to listOf(13), // A
        10 to listOf(14, 15), // A# / Bb
        11 to listOf(16), // B
    )

    private val NB_NOTE_TO_NB_SEMITONE = mapOf(
        2 to listOf(0.5, 1.0),
        3 to listOf(1.5, 2.0),
        4 to listOf(2.0, 2.5, 3.0),
        5 to listOf(3.0, 3.5, 4.0),
        6 to listOf(4.0, 4.5),
        7 to listOf(5.0, 5.5),
        8 to listOf(6.0),
    )

    private val MAJOR_SCALE_INTERVALS_QUALITIES_INDEX = listOf(1, 1, 1, 1, 1, 1) // 2 3 4 5 6 7
    private val MINOR_NATURAL_SCALE_INTERVALS_QUALITIES_INDEX = listOf(1, 0, 1, 1, 0, 0) // 2 b3 4 5 b6 b7
    private val MINOR_HARMONIC_SCALE_INTERVALS_QUALITIES_INDEX = listOf(1, 0, 1, 1, 0, 1) // 2 b3 4 5 b6 7
    private val MINOR_MELODIC_SCALE_INTERVALS_QUALITIES_INDEX = listOf(1, 0, 1, 1, 1, 1) // 2 b3 4 5 6 7
    private val MAJOR_PENTATONIC_SCALE_INTERVALS_QUALITIES_INDEX = listOf(1, 1, 1, 1) // 2 3 5 6
    private val MINOR_PENTATONIC_SCALE_INTERVALS_QUALITIES_INDEX = listOf(0, 1, 1, 0) // 3b 4 5 b7
    private val MAJOR_BLUES_SCALE_INTERVALS_QUALITIES_INDEX = listOf(1, 0, 1, 1, 1) // 2 b3 3 5 6
    private val MINOR_BLUES_SCALE_INTERVALS_QUALITIES_INDEX = listOf(0, 1, 2, 1, 0) // b3 4 4# 5 b7

    private val MAJOR_SCALE_NB_NOTE = listOf(2, 3, 4, 5, 6, 7)
    private val MINOR_NATURAL_SCALE_NB_NOTE = listOf(2, 3, 4, 5, 6, 7)
    private val MINOR_HARMONIC_SCALE_NB_NOTE = listOf(2, 3, 4, 5, 6, 7)
    private val MINOR_MELODIC_SCALE_NB_NOTE = listOf(2, 3, 4, 5, 6, 7)
    private val MAJOR_PENTATONIC_SCALE_NB_NOTE = listOf(2, 3, 5, 6)
    private val MINOR_PENTATONIC_SCALE_NB_NOTE = listOf(3, 4, 5, 7)
    private val MAJOR_BLUES_SCALE_NB_NOTE = listOf(2, 3, 3, 5, 6)
    private val MINOR_BLUES_SCALE_NB_NOTE = listOf(3, 4, 4, 5, 7)

    fun computeCorrectScale(context: Context, startNoteIndex: Int, scaleIndex: Int): Scale {

        listScale = context.resources.getStringArray(R.array.list_scales)
        listNoteWithAlteration = context.resources.getStringArray(R.array.list_notes_with_alterations)

        val startNote = Note().apply {
            noteIndex = startNoteIndex
            noteValue = listNoteWithAlteration[startNoteIndex]
        }

        // Get correct info about the scale
        val scaleValue = listScale[scaleIndex]
        val scale = getCorrectScaleInfo(context, scaleValue).apply {
            this.name = scaleValue
            this.tonicNote = startNote
        }

        // Return the correct scale
        scale.notes = mutableListOf<String>().apply {

            // We add the first element --> Tonic
            add(startNote.noteValue)

            for (counter in scale.nbNotes.indices) {

                var correctNote = computeCorrectNote(context, startNote, scale, counter)

                if (scaleValue.contains("Pentatonique")) {
                    if (!listNoteWithAlteration.contains(correctNote)) {
                        val startNoteKey = NOTE_INDEX_TO_EQUAL_NOTES.entries.find { map ->
                            map.value.contains(startNote.noteIndex)
                        }?.key ?: 0

                        val list = NOTE_INDEX_TO_EQUAL_NOTES[startNoteKey]
                        if (list?.size == 2) {
                            val newStartNote = Note().apply {
                                if (startNote.noteIndex == list[0]) {
                                    noteIndex = list[1]
                                    noteValue = listNoteWithAlteration[list[1]]
                                } else if (startNote.noteIndex == list[1]) {
                                    noteIndex = list[0]
                                    noteValue = listNoteWithAlteration[list[0]]
                                }
                            }

                            correctNote = computeCorrectNote(
                                context,
                                newStartNote,
                                scale,
                                counter
                            )
                        }
                    }
                }

                add(correctNote)
            }

            // We add the first element --> Octave (= tonic)
            add(startNote.noteValue)
        }

        return scale
    }

    /**
     * Compute the correct note
     */
    private fun computeCorrectNote(
        context: Context,
        startNote: Note,
        scale: Scale,
        counter: Int
    ): String {

        // Find the note to reach
        var noteToReach = computeNoteToReachFromTonic(context, startNote.noteValue, scale.nbNotes[counter])

        // Compute the tone difference between the startNote and the note to reach (without alteration)
        val currentToneDifferenceBetweenNotes = computeCurrentTonesBetweenNotes(
            startNote.noteIndex,
            listNoteWithAlteration.indexOf(noteToReach)
        )

        // Compute the tone difference that should exist between the startNote and the note to reach
        val realToneDifferenceBetweenNote = computeRealTonesBetweenNotes(
            scale.nbNotes[counter],
            scale.intervalsQualities[counter]
        )

        val toneDifferenceBetweenCurrentAndReal = realToneDifferenceBetweenNote - currentToneDifferenceBetweenNotes
        val semiToneDifferenceBetweenCurrentAndReal = abs(toneDifferenceBetweenCurrentAndReal / SEMITONE_VALUE).toInt()

        if (semiToneDifferenceBetweenCurrentAndReal != 0 && semiToneDifferenceBetweenCurrentAndReal != NB_NOTES_MIXING_SAME_NOTE) {
            for (i in 0 until semiToneDifferenceBetweenCurrentAndReal) {
                if (toneDifferenceBetweenCurrentAndReal < 0) {
                    noteToReach += FLAT_SYMBOL
                } else if (toneDifferenceBetweenCurrentAndReal > 0) {
                    noteToReach += SHARP_SYMBOL
                }
            }
        }

        return noteToReach
    }

    /**
     * Compute the note to reach.
     * WARNING : It's a computation without alteration !
     */
    private fun computeNoteToReachFromTonic(context: Context, startNoteValue: String, nbTone: Int): String {
        listNoteWithoutAlteration = context.resources.getStringArray(R.array.list_notes_without_alteration)
        val startNoteIndexWithoutAlteration = listNoteWithoutAlteration.indexOf(startNoteValue.substring(0, 1))

        // -1 cause we didn't count the startNote when we count
        val noteToReachIndex =
            abs(NB_NOTES_WITHOUT_ALTERATION + (startNoteIndexWithoutAlteration + nbTone - 1)) % NB_NOTES_WITHOUT_ALTERATION
        return listNoteWithoutAlteration[noteToReachIndex]
    }

    /**
     * Compute current tone difference between a start note and a note to reach.
     */
    private fun computeCurrentTonesBetweenNotes(
        startNote: Int,
        noteToReach: Int,
    ): Double {

        val startNoteKey = NOTE_INDEX_TO_EQUAL_NOTES.entries.find { map ->
            map.value.contains(startNote)
        }?.key ?: 0

        val reachNoteKey = NOTE_INDEX_TO_EQUAL_NOTES.entries.find { map ->
            map.value.contains(noteToReach)
        }?.key ?: 0

        var tonsBetweenNotes = 0.0
        var diff = abs(startNoteKey - reachNoteKey)
        if (startNoteKey > reachNoteKey) {
            diff = NB_NOTES_MIXING_SAME_NOTE - diff
        }

        for (i in 0 until diff) {
            tonsBetweenNotes += SEMITONE_VALUE
        }

        return tonsBetweenNotes
    }

    /**
     * Compute real tones between notes.
     */
    private fun computeRealTonesBetweenNotes(nbNote: Int, intervalQuality: Int): Double {
        return NB_NOTE_TO_NB_SEMITONE[nbNote]?.get(intervalQuality) ?: 0.0
    }

    /**
     * Return Scale containing :
     *  - Nb notes between startNote and interval to reach
     *  - Qualities of interval (list) --> Major, minor, augmented, diminished, just
     */
    private fun getCorrectScaleInfo(context: Context, scale: String): Scale {

        return Scale().apply {
            when (scale) {
                context.getString(R.string.tone_major) -> {
                    nbNotes = MAJOR_SCALE_NB_NOTE
                    intervalsQualities = MAJOR_SCALE_INTERVALS_QUALITIES_INDEX
                }
                context.getString(R.string.tone_minor_natural) -> {
                    nbNotes = MINOR_NATURAL_SCALE_NB_NOTE
                    intervalsQualities = MINOR_NATURAL_SCALE_INTERVALS_QUALITIES_INDEX
                }
                context.getString(R.string.tone_minor_harmonic) -> {
                    nbNotes = MINOR_HARMONIC_SCALE_NB_NOTE
                    intervalsQualities = MINOR_HARMONIC_SCALE_INTERVALS_QUALITIES_INDEX
                }
                context.getString(R.string.tone_minor_melodic) -> {
                    nbNotes = MINOR_MELODIC_SCALE_NB_NOTE
                    intervalsQualities = MINOR_MELODIC_SCALE_INTERVALS_QUALITIES_INDEX
                }
                context.getString(R.string.tone_pentatonic_major) -> {
                    nbNotes = MAJOR_PENTATONIC_SCALE_NB_NOTE
                    intervalsQualities = MAJOR_PENTATONIC_SCALE_INTERVALS_QUALITIES_INDEX
                }
                context.getString(R.string.tone_pentatonic_minor) -> {
                    nbNotes = MINOR_PENTATONIC_SCALE_NB_NOTE
                    intervalsQualities = MINOR_PENTATONIC_SCALE_INTERVALS_QUALITIES_INDEX
                }
                context.getString(R.string.tone_blues_major) -> {
                    nbNotes = MAJOR_BLUES_SCALE_NB_NOTE
                    intervalsQualities = MAJOR_BLUES_SCALE_INTERVALS_QUALITIES_INDEX
                }
                context.getString(R.string.tone_blues_minor) -> {
                    nbNotes = MINOR_BLUES_SCALE_NB_NOTE
                    intervalsQualities = MINOR_BLUES_SCALE_INTERVALS_QUALITIES_INDEX
                }
                else -> {
                    nbNotes = MAJOR_SCALE_NB_NOTE
                    intervalsQualities = MAJOR_SCALE_INTERVALS_QUALITIES_INDEX
                }
            }
        }
    }

    fun alteredScale(context: Context, correctScale: Scale): Scale {
        val shouldBeAltered = Math.random() < 0.5
        return if (shouldBeAltered) {
            val alterationPlace = Random.nextInt(1, correctScale.notes.size - 2)
            val randomNote = Random.nextInt(NB_ALL_NOTES)

            return correctScale.copy().apply {
                notes = correctScale.notes.toMutableList().apply {
                    this[alterationPlace] =
                        context.resources.getStringArray(R.array.list_all_notes_with_alterations)[randomNote]
                }
            }
        } else {
            correctScale
        }
    }
}
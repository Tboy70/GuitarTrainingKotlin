package thomas.guitartrainingkotlin

import android.content.Context
import android.os.Build
import androidx.test.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import thomas.guitartrainingkotlin.presentation.utils.GameUtils

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class GameUnitTest {

    lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun intervalGameTest() {

        // Minor Second
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 0))   // C
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 0))    // C#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 0))  // Db
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 0))   // D
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 0))    // D#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 0))   // Eb
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 0))    // E
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 0))   // F
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 0))    // F#
        assertEquals("Abb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 0))  // Gb
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 0))  // G
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 0))   // G#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 0)) // Ab
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 0))  // A
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 0))   // A#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 0))  // Bb
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 0))   // B

        // Major Second
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 1))    // C
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 1))   // C#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 1))   // Db
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 1))    // D
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 1))   // D#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 1))    // Eb
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 1))   // E
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 1))    // F
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 1))   // F#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 1))   // Gb
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 1))   // G
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 1))  // G#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 1))  // Ab
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 1))   // A
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 1))  // A#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 1))   // Bb
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 1))  // B

        // Minor Third
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 2))   // C
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 2))    // C#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 2))   // Db
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 2))    // D
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 2))   // D#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 2))   // Eb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 2))    // E
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 2))   // F
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 2))    // F#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 2))  // Gb
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 2))  // G
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 2))   // G#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 2))  // Ab
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 2))   // A
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 2))  // A#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 2))  // Bb
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 2))   // B

        // Major Third
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 3))    // C
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 3))   // C#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 3))    // Db
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 3))   // D
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 3))  // D#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 3))    // Eb
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 3))   // E
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 3))    // F
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 3))   // F#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 3))   // Gb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 3))   // G
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 3))  // G#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 3))   // Ab
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 3))  // A
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 3)) // A#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 3))   // Bb
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 3))  // B

        // Perfect fourth
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 4))    // C
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 4))   // C#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 4))   // Db
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 4))    // D
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 4))   // D#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 4))   // Eb
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 4))    // E
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 4))   // F
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 4))    // F#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 4))   // Gb
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 4))   // G
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 4))  // G#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 4))  // Ab
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 4))   // A
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 4))  // A#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 4))  // Bb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 4))   // B

        // Augmented fourth
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 5))   // C
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 5))  // C#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 5))    // Db
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 5))   // D
        assertEquals("G##", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 5))  // D#
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 5))    // Eb
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 5))   // E
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 5))    // F
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 5))   // F#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 5))    // Gb
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 5))  // G
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 5)) // G#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 5))   // Ab
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 5))  // A
        assertEquals("D##", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 5)) // A#
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 5))   // Bb
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 5))  // B

        // Diminished fifth
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 6))   // C
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 6))    // C#
        assertEquals("Abb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 6))  // Db
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 6))   // D
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 6))    // D#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 6))  // Eb
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 6))   // E
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 6))   // F
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 6))    // F#
        assertEquals("Dbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 6))  // Gb
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 6))  // G
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 6))   // G#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 6)) // Ab
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 6))  // A
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 6))   // A#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 6))  // Bb
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 6))   // B

        // Perfect fifth
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 7))    // C
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 7))   // C#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 7))   // Db
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 7))    // D
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 7))   // D#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 7))   // Eb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 7))    // E
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 7))    // F
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 7))   // F#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 7))   // Gb
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 7))   // G
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 7))  // G#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 7))  // Ab
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 7))   // A
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 7))  // A#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 7))   // Bb
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 7))  // B

        // Augmented fifth
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 8))   // C
        assertEquals("G##", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 8))  // C#
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 8))    // Db
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 8))   // D
        assertEquals("A##", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 8))  // D#
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 8))    // Eb
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 8))   // E
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 8))   // F
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 8))  // F#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 8))    // Gb
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 8))  // G
        assertEquals("D##", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 8)) // G#
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 8))   // Ab
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 8))  // A
        assertEquals("E##", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 8)) // A#
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 8))  // Bb
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 8)) // B

        // Minor sixth
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 9))  // C
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 9))   // C#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 9)) // Db
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 9))  // D
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 9))   // D#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 9))  // Eb
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 9))   // E
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 9))  // F
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 9))   // F#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 9)) // Gb
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 9)) // G
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 9))  // G#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 9)) // Ab
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 9))  // A
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 9)) // A#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 9)) // Bb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 9))  // B

        // Major sixth
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 10))    // C
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 10))   // C#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 10))   // Db
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 10))    // D
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 10))   // D#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 10))    // Eb
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 10))   // E
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 10))    // F
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 10))   // F#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 10))   // Gb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 10))   // G
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 10))  // G#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 10))   // Ab
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 10))  // A
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 10)) // A#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 10))   // Bb
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 10))  // B

        // Minor seventh
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 11))  // C
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 11))   // C#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 11))  // Db
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 11))   // D
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 11))  // D#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 11))  // Eb
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 11))   // E
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 11))  // F
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 11))   // F#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 11))  // Gb
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 11))  // G
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 11)) // G#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 11)) // Ab
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 11))  // A
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 11)) // A#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 11)) // Bb
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 11))  // B

        // Major seventh
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 12))    // C
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 12))   // C#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 12))    // Db
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 12))   // D
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 12))  // D#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 12))    // Eb
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 12))   // E
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 12))    // F
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 12))   // F#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 12))    // Gb
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 12))  // G
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 12)) // G#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 12))   // Ab
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 12))  // A
        assertEquals("G##", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 12)) // A#
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 12))   // Bb
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 12))  // B

        // Octave
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 13))   // C
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 13))  // C#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 13))  // Db
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 13))   // D
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 13))  // D#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 13))  // Eb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 13))   // E
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 13))   // F
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 13))  // F#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 13))  // Gb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 13))  // G
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 13)) // G#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 13)) // Ab
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 13))  // A
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 13)) // A#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 13)) // Bb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 13))  // B
    }

    @Test
    fun reversedIntervalGameTest() {

        // Minor Second
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 0))   // C
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 0))    // C#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 0))  // Db
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 0))   // D
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 0))    // D#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 0))   // Eb
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 0))    // E
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 0))   // F
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 0))    // F#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 0))  // Gb
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 0))  // G
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 0))   // G#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 0)) // Ab
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 0))  // A
        assertEquals("G##", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 0))   // A#
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 0))  // Bb
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 0))   // B

        // Major Second
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 1))   // C
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 1))    // C#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 1))  // Db
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 1))   // D
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 1))    // D#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 1))   // Eb
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 1))    // E
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 1))   // F
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 1))    // F#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 1))  // Gb
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 1))  // G
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 1))   // G#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 1)) // Ab
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 1))  // A
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 1))   // A#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 1))  // Bb
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 1))   // B

        // Minor Third
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 2))   // C
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 2))    // C#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 2))  // Db
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 2))   // D
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 2))    // D#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 2))   // Eb
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 2))    // E
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 2))   // F
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 2))    // F#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 2))  // Gb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 2))  // G
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 2))   // G#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 2)) // Ab
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 2))  // A
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 2))   // A#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 2))  // Bb
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 2))   // B

        // Major Third
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 3))   // C
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 3))    // C#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 3))  // Db
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 3))   // D
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 3))    // D#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 3))   // Eb
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 3))    // E
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 3))   // F
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 3))    // F#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 3))  // Gb
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 3))  // G
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 3))   // G#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 3)) // Ab
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 3))  // A
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 3))   // A#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 3))  // Bb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 3))   // B

        // Perfect fourth
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 4))    // C
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 4))   // C#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 4))   // Db
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 4))    // D
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 4))   // D#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 4))   // Eb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 4))    // E
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 4))   // F
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 4))    // F#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 4))   // Gb
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 4))   // G
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 4))  // G#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 4))  // Ab
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 4))   // A
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 4))  // A#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 4))  // Bb
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 4))   // B

        // Augmented fourth
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 5))    // C
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 5))   // C#
        assertEquals("Abb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 5))   // Db
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 5))    // D
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 5))   // D#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 5))   // Eb
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 5))    // E
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 5))   // F
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 5))    // F#
        assertEquals("Dbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 5))   // Gb
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 5))   // G
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 5))  // G#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 5))  // Ab
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 5))   // A
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 5))  // A#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 5))  // Bb
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 5))   // B

        // Diminished fifth
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 6))    // C
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 6))   // C#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 6))   // Db
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 6))    // D
        assertEquals("G##", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 6))   // D#
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 6))   // Eb
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 6))    // E
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 6))   // F
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 6))    // F#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 6))   // Gb
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 6))   // G
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 6))  // G#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 6))  // Ab
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 6))   // A
        assertEquals("D##", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 6))  // A#
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 6))  // Bb
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 6))   // B

        // Perfect fifth
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 7))    // C
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 7))   // C#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 7))   // Db
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 7))    // D
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 7))   // D#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 7))   // Eb
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 7))    // E
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 7))   // F
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 7))    // F#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 7))   // Gb
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 7))   // G
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 7))  // G#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 7))  // Ab
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 7))   // A
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 7))  // A#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 7))  // Bb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 7))   // B

        // Augmented fifth
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 8))    // C
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 8))   // C#
        assertEquals("Gbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 8))   // Db
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 8))    // D
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 8))   // D#
        assertEquals("Abb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 8))   // Eb
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 8))    // E
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 8))   // F
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 8))    // F#
        assertEquals("Cbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 8))   // Gb
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 8))   // G
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 8))  // G#
        assertEquals("Dbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 8))  // Ab
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 8))   // A
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 8))  // A#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 8))  // Bb
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 8))   // B

        // Minor sixth
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 9))    // C
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 9))   // C#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 9))   // Db
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 9))    // D
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 9))   // D#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 9))   // Eb
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 9))    // E
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 9))   // F
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 9))    // F#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 9))   // Gb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 9))   // G
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 9))  // G#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 9))  // Ab
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 9))   // A
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 9))  // A#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 9))  // Bb
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 9))   // B

        // Major sixth
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 10))    // C
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 10))   // C#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 10))   // Db
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 10))    // D
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 10))   // D#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 10))   // Eb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 10))    // E
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 10))   // F
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 10))    // F#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 10))   // Gb
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 10))   // G
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 10))  // G#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 10))  // Ab
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 10))   // A
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 10))  // A#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 10))  // Bb
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 10))   // B

        // Minor seventh
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 11))    // C
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 11))   // C#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 11))   // Db
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 11))    // D
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 11))   // D#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 11))   // Eb
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 11))    // E
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 11))   // F
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 11))    // F#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 11))   // Gb
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 11))   // G
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 11))  // G#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 11))  // Ab
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 11))   // A
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 11))  // A#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 11))  // Bb
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 11))   // B

        // Major seventh
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 12))    // C
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 12))   // C#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 12))   // Db
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 12))    // D
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 12))   // D#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 12))   // Eb
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 12))    // E
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 12))   // F
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 12))    // F#
        assertEquals("Abb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 12))   // Gb
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 12))   // G
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 12))  // G#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 12))  // Ab
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 12))   // A
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 12))  // A#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 12))  // Bb
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 12))   // B

        // Octave
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 13))   // C
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 13))  // C#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 13))  // Db
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 13))   // D
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 13))  // D#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 13))  // Eb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 13))   // E
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 13))   // F
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 13))  // F#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 13))  // Gb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 13))  // G
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 13)) // G#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 13)) // Ab
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 13))  // A
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 13)) // A#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 13)) // Bb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 13))  // B
    }

}
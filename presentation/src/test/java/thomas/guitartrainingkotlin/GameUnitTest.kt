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

    private lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun intervalGameTest() {

        /**
         * L'interval de X est ...
         */

        // Unisson
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 0))   // C
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 0))  // C#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 0))  // Db
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 0))   // D
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 0))  // D#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 0))  // Eb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 0))   // E
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 0))   // F
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 0))  // F#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 0))  // Gb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 0))  // G
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 0)) // G#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 0)) // Ab
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 0))  // A
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 0)) // A#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 0)) // Bb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 0))  // B

        // Minor Second
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 1))   // C
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 1))    // C#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 1))  // Db
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 1))   // D
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 1))    // D#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 1))   // Eb
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 1))    // E
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 1))   // F
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 1))    // F#
        assertEquals("Abb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 1))  // Gb
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 1))  // G
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 1))   // G#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 1)) // Ab
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 1))  // A
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 1))   // A#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 1))  // Bb
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 1))   // B

        // Major Second
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 2))    // C
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 2))   // C#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 2))   // Db
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 2))    // D
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 2))   // D#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 2))    // Eb
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 2))   // E
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 2))    // F
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 2))   // F#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 2))   // Gb
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 2))   // G
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 2))  // G#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 2))  // Ab
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 2))   // A
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 2))  // A#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 2))   // Bb
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 2))  // B

        // Minor Third
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 3))   // C
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 3))    // C#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 3))   // Db
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 3))    // D
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 3))   // D#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 3))   // Eb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 3))    // E
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 3))   // F
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 3))    // F#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 3))  // Gb
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 3))  // G
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 3))   // G#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 3))  // Ab
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 3))   // A
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 3))  // A#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 3))  // Bb
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 3))   // B

        // Major Third
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 4))    // C
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 4))   // C#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 4))    // Db
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 4))   // D
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 4))  // D#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 4))    // Eb
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 4))   // E
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 4))    // F
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 4))   // F#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 4))   // Gb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 4))   // G
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 4))  // G#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 4))   // Ab
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 4))  // A
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 4)) // A#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 4))   // Bb
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 4))  // B

        // Diminished fourth
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 5))    // C
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 5))   // C#
        assertEquals("Gbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 5))   // Db
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 5))    // D
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 5))   // D#
        assertEquals("Abb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 5))   // Eb
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 5))    // E
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 5))   // F
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 5))    // F#
        assertEquals("Cbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 5))   // Gb
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 5))   // G
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 5))  // G#
        assertEquals("Dbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 5))  // Ab
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 5))   // A
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 5))  // A#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 5))  // Bb
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 5))   // B

        // Perfect fourth
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 6))    // C
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 6))   // C#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 6))   // Db
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 6))    // D
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 6))   // D#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 6))   // Eb
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 6))    // E
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 6))   // F
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 6))    // F#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 6))   // Gb
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 6))   // G
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 6))  // G#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 6))  // Ab
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 6))   // A
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 6))  // A#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 6))  // Bb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 6))   // B

        // Augmented fourth
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 7))   // C
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 7))  // C#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 7))    // Db
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 7))   // D
        assertEquals("G##", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 7))  // D#
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 7))    // Eb
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 7))   // E
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 7))    // F
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 7))   // F#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 7))    // Gb
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 7))  // G
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 7)) // G#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 7))   // Ab
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 7))  // A
        assertEquals("D##", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 7)) // A#
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 7))   // Bb
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 7))  // B

        // Diminished fifth
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 8))   // C
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 8))    // C#
        assertEquals("Abb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 8))  // Db
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 8))   // D
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 8))    // D#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 8))  // Eb
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 8))   // E
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 8))   // F
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 8))    // F#
        assertEquals("Dbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 8))  // Gb
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 8))  // G
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 8))   // G#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 8)) // Ab
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 8))  // A
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 8))   // A#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 8))  // Bb
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 8))   // B

        // Perfect fifth
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 9))    // C
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 9))   // C#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 9))   // Db
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 9))    // D
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 9))   // D#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 9))   // Eb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 9))    // E
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 9))    // F
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 9))   // F#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 9))   // Gb
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 9))   // G
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 9))  // G#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 9))  // Ab
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 9))   // A
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 9))  // A#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 9))   // Bb
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 9))  // B

        // Augmented fifth
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 10))   // C
        assertEquals("G##", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 10))  // C#
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 10))    // Db
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 10))   // D
        assertEquals("A##", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 10))  // D#
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 10))    // Eb
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 10))   // E
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 10))   // F
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 10))  // F#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 10))    // Gb
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 10))  // G
        assertEquals("D##", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 10)) // G#
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 10))   // Ab
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 10))  // A
        assertEquals("E##", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 10)) // A#
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 10))  // Bb
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 10)) // B

        // Minor sixth
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 11))  // C
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 11))   // C#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 11)) // Db
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 11))  // D
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 11))   // D#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 11))  // Eb
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 11))   // E
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 11))  // F
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 11))   // F#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 11)) // Gb
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 11)) // G
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 11))  // G#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 11)) // Ab
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 11))  // A
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 11)) // A#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 11)) // Bb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 11))  // B

        // Major sixth
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 12))    // C
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 12))   // C#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 12))   // Db
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 12))    // D
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 12))   // D#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 12))    // Eb
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 12))   // E
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 12))    // F
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 12))   // F#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 12))   // Gb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 12))   // G
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 12))  // G#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 12))   // Ab
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 12))  // A
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 12)) // A#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 12))   // Bb
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 12))  // B

        // Minor seventh
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 13))  // C
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 13))   // C#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 13))  // Db
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 13))   // D
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 13))  // D#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 13))  // Eb
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 13))   // E
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 13))  // F
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 13))   // F#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 13))  // Gb
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 13))  // G
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 13)) // G#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 13)) // Ab
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 13))  // A
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 13)) // A#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 13)) // Bb
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 13))  // B

        // Major seventh
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 14))    // C
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 14))   // C#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 14))    // Db
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 14))   // D
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 14))  // D#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 14))    // Eb
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 14))   // E
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 14))    // F
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 14))   // F#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 14))    // Gb
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 14))  // G
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 14)) // G#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 14))   // Ab
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 14))  // A
        assertEquals("G##", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 14)) // A#
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 14))   // Bb
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 14))  // B

        // Octave
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 0, 0, 15))   // C
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 0, 1, 15))  // C#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 0, 2, 15))  // Db
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 0, 3, 15))   // D
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 0, 4, 15))  // D#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 0, 5, 15))  // Eb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 0, 6, 15))   // E
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 0, 7, 15))   // F
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 0, 8, 15))  // F#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 0, 9, 15))  // Gb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 0, 10, 15))  // G
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 0, 11, 15)) // G#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 0, 12, 15)) // Ab
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 0, 13, 15))  // A
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 0, 14, 15)) // A#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 0, 15, 15)) // Bb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 0, 16, 15))  // B

        /**
         * X est l'interval de ...
         */

        // Unisson
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 0))   // C
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 0))  // C#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 0))  // Db
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 0))   // D
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 0))  // D#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 0))  // Eb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 0))   // E
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 0))   // F
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 0))  // F#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 0))  // Gb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 0))  // G
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 0)) // G#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 0)) // Ab
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 0))  // A
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 0)) // A#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 0)) // Bb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 0))  // B

        // Minor Second
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 1))   // C
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 1))    // C#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 1))  // Db
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 1))   // D
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 1))    // D#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 1))   // Eb
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 1))    // E
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 1))   // F
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 1))    // F#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 1))  // Gb
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 1))  // G
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 1))   // G#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 1)) // Ab
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 1))  // A
        assertEquals("G##", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 1))   // A#
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 1))  // Bb
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 1))   // B

        // Major Second
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 2))   // C
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 2))    // C#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 2))  // Db
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 2))   // D
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 2))    // D#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 2))   // Eb
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 2))    // E
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 2))   // F
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 2))    // F#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 2))  // Gb
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 2))  // G
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 2))   // G#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 2)) // Ab
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 2))  // A
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 2))   // A#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 2))  // Bb
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 2))   // B

        // Minor Third
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 3))   // C
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 3))    // C#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 3))  // Db
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 3))   // D
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 3))    // D#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 3))   // Eb
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 3))    // E
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 3))   // F
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 3))    // F#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 3))  // Gb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 3))  // G
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 3))   // G#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 3)) // Ab
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 3))  // A
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 3))   // A#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 3))  // Bb
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 3))   // B

        // Major Third
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 4))   // C
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 4))    // C#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 4))  // Db
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 4))   // D
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 4))    // D#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 4))   // Eb
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 4))    // E
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 4))   // F
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 4))    // F#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 4))  // Gb
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 4))  // G
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 4))   // G#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 4)) // Ab
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 4))  // A
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 4))   // A#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 4))  // Bb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 4))   // B

        // Diminished fourth
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 5))    // C
        assertEquals("G##", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 5))   // C#
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 5))   // Db
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 5))    // D
        assertEquals("A##", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 5))   // D#
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 5))   // Eb
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 5))    // E
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 5))   // F
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 5))    // F#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 5))   // Gb
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 5))   // G
        assertEquals("D##", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 5))  // G#
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 5))  // Ab
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 5))   // A
        assertEquals("E##", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 5))  // A#
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 5))  // Bb
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 5))   // B

        // Perfect fourth
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 6))    // C
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 6))   // C#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 6))   // Db
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 6))    // D
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 6))   // D#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 6))   // Eb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 6))    // E
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 6))   // F
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 6))    // F#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 6))   // Gb
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 6))   // G
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 6))  // G#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 6))  // Ab
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 6))   // A
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 6))  // A#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 6))  // Bb
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 6))   // B

        // Augmented fourth
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 7))    // C
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 7))   // C#
        assertEquals("Abb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 7))   // Db
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 7))    // D
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 7))   // D#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 7))   // Eb
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 7))    // E
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 7))   // F
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 7))    // F#
        assertEquals("Dbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 7))   // Gb
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 7))   // G
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 7))  // G#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 7))  // Ab
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 7))   // A
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 7))  // A#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 7))  // Bb
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 7))   // B

        // Diminished fifth
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 8))    // C
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 8))   // C#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 8))   // Db
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 8))    // D
        assertEquals("G##", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 8))   // D#
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 8))   // Eb
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 8))    // E
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 8))   // F
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 8))    // F#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 8))   // Gb
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 8))   // G
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 8))  // G#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 8))  // Ab
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 8))   // A
        assertEquals("D##", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 8))  // A#
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 8))  // Bb
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 8))   // B

        // Perfect fifth
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 9))    // C
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 9))   // C#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 9))   // Db
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 9))    // D
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 9))   // D#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 9))   // Eb
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 9))    // E
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 9))   // F
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 9))    // F#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 9))   // Gb
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 9))   // G
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 9))  // G#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 9))  // Ab
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 9))   // A
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 9))  // A#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 9))  // Bb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 9))   // B

        // Augmented fifth
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 10))    // C
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 10))   // C#
        assertEquals("Gbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 10))   // Db
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 10))    // D
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 10))   // D#
        assertEquals("Abb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 10))   // Eb
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 10))    // E
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 10))   // F
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 10))    // F#
        assertEquals("Cbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 10))   // Gb
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 10))   // G
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 10))  // G#
        assertEquals("Dbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 10))  // Ab
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 10))   // A
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 10))  // A#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 10))  // Bb
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 10))   // B

        // Minor sixth
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 11))    // C
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 11))   // C#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 11))   // Db
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 11))    // D
        assertEquals("F##", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 11))   // D#
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 11))   // Eb
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 11))    // E
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 11))   // F
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 11))    // F#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 11))   // Gb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 11))   // G
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 11))  // G#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 11))  // Ab
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 11))   // A
        assertEquals("C##", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 11))  // A#
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 11))  // Bb
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 11))   // B

        // Major sixth
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 12))    // C
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 12))   // C#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 12))   // Db
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 12))    // D
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 12))   // D#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 12))   // Eb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 12))    // E
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 12))   // F
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 12))    // F#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 12))   // Gb
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 12))   // G
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 12))  // G#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 12))  // Ab
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 12))   // A
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 12))  // A#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 12))  // Bb
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 12))   // B

        // Minor seventh
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 13))    // C
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 13))   // C#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 13))   // Db
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 13))    // D
        assertEquals("E#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 13))   // D#
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 13))   // Eb
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 13))    // E
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 13))   // F
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 13))    // F#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 13))   // Gb
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 13))   // G
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 13))  // G#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 13))  // Ab
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 13))   // A
        assertEquals("B#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 13))  // A#
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 13))  // Bb
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 13))   // B

        // Major seventh
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 14))    // C
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 14))   // C#
        assertEquals("Ebb", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 14))   // Db
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 14))    // D
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 14))   // D#
        assertEquals("Fb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 14))   // Eb
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 14))    // E
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 14))   // F
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 14))    // F#
        assertEquals("Abb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 14))   // Gb
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 14))   // G
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 14))  // G#
        assertEquals("Bbb", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 14))  // Ab
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 14))   // A
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 14))  // A#
        assertEquals("Cb", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 14))  // Bb
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 14))   // B

        // Octave
        assertEquals("C", GameUtils.computeCorrectNote(instrumentationContext, 1, 0, 15))   // C
        assertEquals("C#", GameUtils.computeCorrectNote(instrumentationContext, 1, 1, 15))  // C#
        assertEquals("Db", GameUtils.computeCorrectNote(instrumentationContext, 1, 2, 15))  // Db
        assertEquals("D", GameUtils.computeCorrectNote(instrumentationContext, 1, 3, 15))   // D
        assertEquals("D#", GameUtils.computeCorrectNote(instrumentationContext, 1, 4, 15))  // D#
        assertEquals("Eb", GameUtils.computeCorrectNote(instrumentationContext, 1, 5, 15))  // Eb
        assertEquals("E", GameUtils.computeCorrectNote(instrumentationContext, 1, 6, 15))   // E
        assertEquals("F", GameUtils.computeCorrectNote(instrumentationContext, 1, 7, 15))   // F
        assertEquals("F#", GameUtils.computeCorrectNote(instrumentationContext, 1, 8, 15))  // F#
        assertEquals("Gb", GameUtils.computeCorrectNote(instrumentationContext, 1, 9, 15))  // Gb
        assertEquals("G", GameUtils.computeCorrectNote(instrumentationContext, 1, 10, 15))  // G
        assertEquals("G#", GameUtils.computeCorrectNote(instrumentationContext, 1, 11, 15)) // G#
        assertEquals("Ab", GameUtils.computeCorrectNote(instrumentationContext, 1, 12, 15)) // Ab
        assertEquals("A", GameUtils.computeCorrectNote(instrumentationContext, 1, 13, 15))  // A
        assertEquals("A#", GameUtils.computeCorrectNote(instrumentationContext, 1, 14, 15)) // A#
        assertEquals("Bb", GameUtils.computeCorrectNote(instrumentationContext, 1, 15, 15)) // Bb
        assertEquals("B", GameUtils.computeCorrectNote(instrumentationContext, 1, 16, 15))  // B
    }

    @Test
    fun reversedIntervalGameTest() {
        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_unisson),
                instrumentationContext.resources.getString(R.string.interval_octave)
            ), GameUtils.computeReversedInterval(instrumentationContext, 0)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_second_minor),
                instrumentationContext.resources.getString(R.string.interval_seventh_major)
            ), GameUtils.computeReversedInterval(instrumentationContext, 1)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_second_major),
                instrumentationContext.resources.getString(R.string.interval_seventh_minor)
            ), GameUtils.computeReversedInterval(instrumentationContext, 2)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_third_minor),
                instrumentationContext.resources.getString(R.string.interval_sixth_major)
            ), GameUtils.computeReversedInterval(instrumentationContext, 3)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_third_major),
                instrumentationContext.resources.getString(R.string.interval_sixth_minor)
            ), GameUtils.computeReversedInterval(instrumentationContext, 4)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_diminished_fourth),
                instrumentationContext.resources.getString(R.string.interval_augmented_fifth)
            ), GameUtils.computeReversedInterval(instrumentationContext, 5)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_fourth),
                instrumentationContext.resources.getString(R.string.interval_fifth)
            ), GameUtils.computeReversedInterval(instrumentationContext, 6)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_augmented_fourth),
                instrumentationContext.resources.getString(R.string.interval_diminished_fifth)
            ), GameUtils.computeReversedInterval(instrumentationContext, 7)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_diminished_fifth),
                instrumentationContext.resources.getString(R.string.interval_augmented_fourth)
            ), GameUtils.computeReversedInterval(instrumentationContext, 8)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_fifth),
                instrumentationContext.resources.getString(R.string.interval_fourth)
            ), GameUtils.computeReversedInterval(instrumentationContext, 9)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_augmented_fifth),
                instrumentationContext.resources.getString(R.string.interval_diminished_fourth)
            ), GameUtils.computeReversedInterval(instrumentationContext, 10)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_sixth_minor),
                instrumentationContext.resources.getString(R.string.interval_third_major)
            ), GameUtils.computeReversedInterval(instrumentationContext, 11)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_sixth_major),
                instrumentationContext.resources.getString(R.string.interval_third_minor)
            ), GameUtils.computeReversedInterval(instrumentationContext, 12)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_seventh_minor),
                instrumentationContext.resources.getString(R.string.interval_second_major)
            ), GameUtils.computeReversedInterval(instrumentationContext, 13)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_seventh_major),
                instrumentationContext.resources.getString(R.string.interval_second_minor)
            ), GameUtils.computeReversedInterval(instrumentationContext, 14)
        )

        assertEquals(
            Pair(
                instrumentationContext.resources.getString(R.string.interval_octave),
                instrumentationContext.resources.getString(R.string.interval_unisson)
            ), GameUtils.computeReversedInterval(instrumentationContext, 15)
        )
    }
}
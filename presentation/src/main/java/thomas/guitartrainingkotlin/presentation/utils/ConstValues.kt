package thomas.guitartrainingkotlin.presentation.utils

object ConstValues {

    // GENERIC CONST
    const val CONST_ERROR = -1
    const val CONST_DEFAULT_TIMESTAMP = 1000000000000

    // PROGRAM CONST
    private const val DEFAULT_PROGRAM_THEORETICAL_GUITAR = "1"
    private const val DEFAULT_PROGRAM_PRACTICAL_GUITAR = "2"
    private const val DEFAULT_PROGRAM_THEORETICAL_BASS = "3"
    private const val DEFAULT_PROGRAM_PRACTICAL_BASS = "4"
    val DEFAULT_PROGRAM = listOf(
        DEFAULT_PROGRAM_THEORETICAL_GUITAR,
        DEFAULT_PROGRAM_PRACTICAL_GUITAR,
        DEFAULT_PROGRAM_THEORETICAL_BASS,
        DEFAULT_PROGRAM_PRACTICAL_BASS
    )

    // IDS CONST
    const val USER_ID = "thomas.example.com.guitarTrainingKotlin.baseActivity.USER_ID"
    const val ID_SONG = "thomas.example.com.guitarTrainingKotlin.baseActivity.ID_SONG"
    const val ID_PROGRAM = "thomas.example.com.guitarTrainingKotlin.baseActivity.ID_PROGRAM"

}
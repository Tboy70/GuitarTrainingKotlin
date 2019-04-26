package thomas.guitartrainingkotlin.presentation.utils

object ConstValues {

    // GENERIC CONST
    const val CONST_ERROR = -1
    const val CONST_DEFAULT_TIMESTAMP = 1000000000000

    // MUSIC CONST
    const val NB_NOTES = 12
    const val NB_SCALES = 5
    const val NB_INTERVAL = 13

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

    // GAMES CONST
    const val SCALE_GAME_MODE = 3
    const val INTERVAL_GAME_MODE = 2

    const val INTERVAL_NORMAL_GAME_MODE = 0
    const val INTERVAL_REVERSED_GAME_MODE = 1

    const val SCALE_GAME_FIND_NOTES_MODE = 0
    const val SCALE_GAME_CORRECT_SCALE_MODE = 1
    const val SCALE_GAME_FIND_SCALE_MODE = 2

    // IDS CONST
    const val USER_ID = "thomas.example.com.guitarTrainingKotlin.baseActivity.USER_ID"
    const val ID_SONG = "thomas.example.com.guitarTrainingKotlin.baseActivity.ID_SONG"
    const val ID_PROGRAM = "thomas.example.com.guitarTrainingKotlin.baseActivity.ID_PROGRAM"

}
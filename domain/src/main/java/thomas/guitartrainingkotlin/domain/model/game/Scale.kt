package thomas.guitartrainingkotlin.domain.model.game

data class Scale(
    var nbNotes: List<Int> = listOf(),
    var intervalsQualities: List<Int> = listOf()
)
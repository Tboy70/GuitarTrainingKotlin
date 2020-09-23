package thomas.guitartrainingkotlin.domain.model.game

data class Scale(
    var name : String = "",
    var tonicNote: Note = Note(),
    var nbNotes: List<Int> = listOf(),
    var notes: MutableList<String> = mutableListOf(),
    var intervalsQualities: List<Int> = listOf()
)
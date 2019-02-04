package thomas.example.com.guitarTrainingKotlin.view.state.program

data class UserProgramListViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
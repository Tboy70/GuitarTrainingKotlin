package thomas.example.com.guitarTrainingKotlin.view.state

data class UserProgramListViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
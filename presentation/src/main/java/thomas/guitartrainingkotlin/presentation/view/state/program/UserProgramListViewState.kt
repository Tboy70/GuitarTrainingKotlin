package thomas.guitartrainingkotlin.presentation.view.state.program

data class UserProgramListViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
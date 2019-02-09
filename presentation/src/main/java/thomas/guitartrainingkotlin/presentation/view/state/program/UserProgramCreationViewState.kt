package thomas.guitartrainingkotlin.presentation.view.state.program

data class UserProgramCreationViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
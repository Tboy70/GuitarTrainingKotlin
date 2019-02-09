package thomas.guitartrainingkotlin.presentation.view.state.user

data class CreateAccountFragmentViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
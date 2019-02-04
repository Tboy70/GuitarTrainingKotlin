package thomas.example.com.guitarTrainingKotlin.view.state.user

data class CreateAccountFragmentViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
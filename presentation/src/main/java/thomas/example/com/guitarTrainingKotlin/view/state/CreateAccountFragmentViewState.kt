package thomas.example.com.guitarTrainingKotlin.view.state

data class CreateAccountFragmentViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
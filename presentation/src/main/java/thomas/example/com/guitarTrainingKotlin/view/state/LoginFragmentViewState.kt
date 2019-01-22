package thomas.example.com.guitarTrainingKotlin.view.state

data class LoginFragmentViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
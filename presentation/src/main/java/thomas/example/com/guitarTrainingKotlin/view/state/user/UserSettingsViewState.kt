package thomas.example.com.guitarTrainingKotlin.view.state.user

data class UserSettingsViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
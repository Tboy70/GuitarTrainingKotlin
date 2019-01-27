package thomas.example.com.guitarTrainingKotlin.view.state

data class UserSettingsViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
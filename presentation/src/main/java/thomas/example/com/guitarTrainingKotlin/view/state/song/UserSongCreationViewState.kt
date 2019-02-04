package thomas.example.com.guitarTrainingKotlin.view.state.song

data class UserSongCreationViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
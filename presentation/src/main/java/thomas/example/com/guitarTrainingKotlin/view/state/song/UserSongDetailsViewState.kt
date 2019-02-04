package thomas.example.com.guitarTrainingKotlin.view.state.song

data class UserSongDetailsViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
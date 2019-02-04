package thomas.example.com.guitarTrainingKotlin.view.state.song

data class UserSongListViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
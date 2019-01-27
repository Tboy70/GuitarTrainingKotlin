package thomas.example.com.guitarTrainingKotlin.view.state

data class UserSongListViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
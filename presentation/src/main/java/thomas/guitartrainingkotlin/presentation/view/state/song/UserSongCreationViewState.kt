package thomas.guitartrainingkotlin.presentation.view.state.song

data class UserSongCreationViewState(
    var displayError: Boolean = false,
    var loading: Boolean = false
)
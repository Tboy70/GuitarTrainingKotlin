package thomas.example.com.guitarTrainingKotlin.viewmodel.song

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.interactor.song.UpdateSong
import thomas.example.com.model.Song
import javax.inject.Inject

class UserSongUpdateViewModel @Inject constructor(private var updateSong: UpdateSong) : ViewModel() {

    val updateSongSuccess: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null

    fun checkInformationAndValidateUpdate(idSong: String, titleSong: String, artistSong: String) {
        if (checkInformation(titleSong)) {
            val song = Song()
            song.idSong = idSong
            song.titleSong = titleSong
            song.artistSong = artistSong

            updateSong.subscribe(
                    params = UpdateSong.Params.toUpdate(song),
                    onComplete = {
                        updateSongSuccess.postValue(true)
                    },
                    onError = {
                        errorThrowable = it
                        updateSongSuccess.postValue(false)
                    }
            )
        }
    }

    private fun checkInformation(nameSong: String): Boolean {
        return !nameSong.isEmpty()
    }

    override fun onCleared() {
        super.onCleared()
        updateSong.unsubscribe()
    }
}
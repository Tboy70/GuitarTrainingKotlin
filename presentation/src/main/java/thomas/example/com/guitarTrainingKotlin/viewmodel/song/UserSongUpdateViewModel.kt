package thomas.example.com.guitarTrainingKotlin.viewmodel.song

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import thomas.example.com.interactor.song.UpdateSong
import thomas.example.com.model.Song
import javax.inject.Inject

class UserSongUpdateViewModel @Inject constructor(private var updateSong: UpdateSong) : ViewModel() {

    val updateSongSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val updateSongFailure: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null

    fun checkInformationAndValidateUpdate(idSong: String, titleSong: String, artistSong: String) {
        if (checkInformation(titleSong)) {
            val song = Song()
            song.idSong = idSong
            song.titleSong = titleSong
            song.artistSong = artistSong

            updateSong.execute(
                    onComplete = {

                    },
                    onError = {
                        errorThrowable = it
                        updateSongFailure.postValue(true)
                    },
                    onNext = {
                        if (it) {
                            updateSongSuccess.postValue(true)
                        }
                    }, params = UpdateSong.Params.toUpdate(song))
        }
    }

    private fun checkInformation(nameSong: String): Boolean {
        return !nameSong.isEmpty()
    }
}
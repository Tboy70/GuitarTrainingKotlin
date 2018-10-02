package thomas.example.com.guitarTrainingKotlin.viewmodel.song

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import thomas.example.com.interactor.song.CreateSong
import thomas.example.com.model.Song
import javax.inject.Inject

class UserSongCreationViewModel @Inject constructor(private var createSong: CreateSong) : ViewModel() {

    val creationSongSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val creationSongNotLaunch: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null

    fun checkInformationAndValidateCreation(titleSong: String, artistSong: String, instrumentMode: String) {
        if (checkInformation(titleSong)) {
            val song = Song()
            song.titleSong = titleSong
            song.artistSong = artistSong
            song.idInstrument = instrumentMode

            createSong.execute(
                    onComplete = {

                    },
                    onError = {
                        errorThrowable = it
                        creationSongSuccess.postValue(false)

                    },
                    onNext = {
                        if (!it.isEmpty()) {
                            creationSongSuccess.postValue(true)
                        }
                    }, params = CreateSong.Params.toCreate(song))
        } else {
            creationSongNotLaunch.postValue(true)
        }
    }

    private fun checkInformation(titleSong: String): Boolean {
        return !titleSong.isEmpty()
    }
}
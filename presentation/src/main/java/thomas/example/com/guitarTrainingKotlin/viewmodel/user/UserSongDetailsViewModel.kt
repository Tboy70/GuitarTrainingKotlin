package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.SongObjectWrapper
import thomas.example.com.interactor.song.RemoveSong
import thomas.example.com.interactor.song.RetrieveSongById
import thomas.example.com.interactor.song.SendScoreFeedback
import thomas.example.com.model.ScoreFeedback
import javax.inject.Inject

class UserSongDetailsViewModel @Inject constructor(private val retrieveSongById: RetrieveSongById,
                                                   private val removeSong: RemoveSong,
                                                   private val sendScoreFeedback: SendScoreFeedback) : ViewModel() {

    lateinit var userSongObjectWrapper: SongObjectWrapper

    val finishLoading: MutableLiveData<Boolean> = MutableLiveData()
    val finishRetrieveSongForDetails: MutableLiveData<Boolean> = MutableLiveData()
    val finishSongDeletion: MutableLiveData<Boolean> = MutableLiveData()
    val finishFeedbackSending: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null

    fun getSongById(idProgram: String) {
        retrieveSongById.execute(
                onComplete = {
                    finishLoading.postValue(true)
                },
                onError = {
                    errorThrowable = it
                    finishRetrieveSongForDetails.postValue(false)
                },
                onNext = {
                    userSongObjectWrapper = SongObjectWrapper(it)
                    finishRetrieveSongForDetails.postValue(true)

                }, params = RetrieveSongById.Params.toRetrieve(idProgram))
    }

    fun removeSong(idProgram: String) {
        removeSong.execute(
                onComplete = {
                    finishLoading.postValue(true)
                },
                onError = {
                    errorThrowable = it
                    finishSongDeletion.postValue(false)
                },
                onNext = {
                    if (it) {
                        finishSongDeletion.postValue(true)
                    }

                }, params = RemoveSong.Params.toRemove(idProgram))
    }

    fun sendSongFeedback(scoreFeedbackValue: Int, idSong: String) {

        val scoreFeedback = ScoreFeedback()
        scoreFeedback.scoreFeedback = scoreFeedbackValue

        sendScoreFeedback.execute(
                onComplete = {

                },
                onError = {
                    errorThrowable = it
                    finishFeedbackSending.postValue(false)
                },
                onNext = {
                    finishFeedbackSending.postValue(true)
                }, params = SendScoreFeedback.Params.toSend(scoreFeedback, idSong))
    }
}
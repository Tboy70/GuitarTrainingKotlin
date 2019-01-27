package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import android.util.LongSparseArray
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.view.datawrapper.SongViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.DateTimeUtils
import thomas.example.com.interactor.song.RemoveSong
import thomas.example.com.interactor.song.RetrieveSongById
import thomas.example.com.interactor.song.RetrieveSongScoreHistoric
import thomas.example.com.interactor.song.SendScoreFeedback
import thomas.example.com.model.Score
import thomas.example.com.model.ScoreFeedback
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UserSongDetailsViewModel @Inject constructor(
        private val retrieveSongById: RetrieveSongById,
        private val removeSong: RemoveSong,
        private val sendScoreFeedback: SendScoreFeedback,
        private val retrieveSongScoreHistoric: RetrieveSongScoreHistoric
) : ViewModel() {

    lateinit var userSongViewDataWrapper: SongViewDataWrapper
    lateinit var timestampKeyList: LongSparseArray<Float>

    val finishLoading: MutableLiveData<Boolean> = MutableLiveData()
    val finishRetrieveSongForDetails: MutableLiveData<Boolean> = MutableLiveData()
    val finishSongDeletion: MutableLiveData<Boolean> = MutableLiveData()
    val finishFeedbackSending: MutableLiveData<Boolean> = MutableLiveData()
    val finishRetrieveSongScoreHistoric: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null
    var referenceTimestamp: Long = ConstValues.CONST_DEFAULT_TIMESTAMP

    fun getSongById(idProgram: String) {
        retrieveSongById.subscribe(
                params = RetrieveSongById.Params.toRetrieve(idProgram),
                onSuccess = {
                    userSongViewDataWrapper = SongViewDataWrapper(it)
                    finishRetrieveSongForDetails.postValue(true)
                    finishLoading.postValue(true)
                },
                onError = {
                    errorThrowable = it
                    finishRetrieveSongForDetails.postValue(false)
                }
        )
    }

    fun removeSong(idProgram: String) {
        removeSong.subscribe(
                params = RemoveSong.Params.toRemove(idProgram),
                onComplete = {
                    finishSongDeletion.postValue(true)
                    finishLoading.postValue(true)
                },
                onError = {
                    errorThrowable = it
                    finishSongDeletion.postValue(false)
                }
        )
    }

    fun sendSongFeedback(scoreFeedbackValue: Int, idSong: String) {

        val scoreFeedback = ScoreFeedback()
        scoreFeedback.scoreFeedback = scoreFeedbackValue

        sendScoreFeedback.subscribe(
                params = SendScoreFeedback.Params.toSend(scoreFeedback, idSong),
                onComplete = {
                    finishFeedbackSending.postValue(true)
                },
                onError = {
                    errorThrowable = it
                    finishFeedbackSending.postValue(false)
                }
        )
    }

    fun retrieveSongScoreHistoric(idSong: String) {
        retrieveSongScoreHistoric.subscribe(
                params = RetrieveSongScoreHistoric.Params.toRetrieve(idSong),
                onSuccess = {
                    formatRetrievedList(it)
                },
                onError = {
                    errorThrowable = it
                    finishRetrieveSongScoreHistoric.postValue(false)
                }
        )
    }

    private fun formatRetrievedList(scoreList: List<Score>) {
        timestampKeyList = convertHistoricDatesToTimestamp(scoreList)
        finishRetrieveSongScoreHistoric.postValue(true)
    }

    private fun convertHistoricDatesToTimestamp(scoreList: List<Score>): LongSparseArray<Float> {
        val timestampKeyList = LongSparseArray<Float>()
        val dateFormat = SimpleDateFormat(DateTimeUtils.FROM_API_FORMAT, Locale.FRANCE)

        for (score in scoreList) {
            val timestamp = Timestamp(dateFormat.parse(score.dateScore).time).time / 1000
            if (timestamp < referenceTimestamp) {
                referenceTimestamp = timestamp
            }
            timestampKeyList.put(timestamp, score.valueScore)
        }

        val timestampKeyListMinusReferenceTimestamp = LongSparseArray<Float>()
        for (i in 0 until timestampKeyList.size()) {
            val key = timestampKeyList.keyAt(i)
            timestampKeyListMinusReferenceTimestamp.put(key - referenceTimestamp, timestampKeyList.get(key))
        }

        return timestampKeyListMinusReferenceTimestamp
    }

    override fun onCleared() {
        super.onCleared()
        retrieveSongById.unsubscribe()
        removeSong.unsubscribe()
        sendScoreFeedback.unsubscribe()
        retrieveSongScoreHistoric.unsubscribe()
    }
}
package thomas.example.com.guitarTrainingKotlin.viewmodel.user

import android.util.LongSparseArray
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.SongObjectWrapper
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

    lateinit var userSongObjectWrapper: SongObjectWrapper
    lateinit var timestampKeyList: LongSparseArray<Float>

    val finishLoading: MutableLiveData<Boolean> = MutableLiveData()
    val finishRetrieveSongForDetails: MutableLiveData<Boolean> = MutableLiveData()
    val finishSongDeletion: MutableLiveData<Boolean> = MutableLiveData()
    val finishFeedbackSending: MutableLiveData<Boolean> = MutableLiveData()
    val finishRetrieveSongScoreHistoric: MutableLiveData<Boolean> = MutableLiveData()

    var errorThrowable: Throwable? = null
    var referenceTimestamp: Long = ConstValues.CONST_DEFAULT_TIMESTAMP

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

            }, params = RetrieveSongById.Params.toRetrieve(idProgram)
        )
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

            }, params = RemoveSong.Params.toRemove(idProgram)
        )
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
            }, params = SendScoreFeedback.Params.toSend(scoreFeedback, idSong)
        )
    }

    fun retrieveSongScoreHistoric(idSong: String) {
        retrieveSongScoreHistoric.execute(
            onComplete = {

            },
            onError = {
                errorThrowable = it
                finishRetrieveSongScoreHistoric.postValue(false)
            },
            onNext = {
                formatRetrievedList(it)
            }, params = RetrieveSongScoreHistoric.Params.toRetrieve(idSong)
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
}
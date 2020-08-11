package thomas.guitartrainingkotlin.presentation.viewmodel.user

import android.util.LongSparseArray
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import thomas.guitartrainingkotlin.domain.interactor.song.RemoveSong
import thomas.guitartrainingkotlin.domain.interactor.song.RetrieveSongById
import thomas.guitartrainingkotlin.domain.interactor.song.RetrieveSongScoreHistory
import thomas.guitartrainingkotlin.domain.interactor.song.SendScoreFeedback
import thomas.guitartrainingkotlin.domain.model.Score
import thomas.guitartrainingkotlin.domain.model.ScoreFeedback
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.utils.DateTimeUtils
import thomas.guitartrainingkotlin.presentation.view.datawrapper.SongViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.song.UserSongDetailsViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class UserSongDetailsViewModel @ViewModelInject constructor(
    private val removeSong: RemoveSong,
    private val retrieveSongById: RetrieveSongById,
    private val sendScoreFeedback: SendScoreFeedback,
    private val retrieveSongScoreHistory: RetrieveSongScoreHistory
) : StateViewModel<UserSongDetailsViewState>() {

    override val currentViewState = UserSongDetailsViewState()

    private var idSong: String? = null

    val songDeletedLiveEvent: MutableLiveData<Boolean> = MutableLiveData()
    val songRetrievedLiveData: MutableLiveData<SongViewDataWrapper> = MutableLiveData()
    val scoreHistoryRetrieved: MutableLiveData<LongSparseArray<Float>> = MutableLiveData()

    var referenceTimestamp: Long = ConstValues.CONST_DEFAULT_TIMESTAMP

    fun setIdSong(idSong: String) {
        this.idSong = idSong
    }

    fun getIdSong(): String? {
        return idSong
    }

    fun retrieveSongScoreHistory() {
        viewState.update {
            loading = true
        }
        idSong?.let { idSong ->
            compositeDisposable?.add(
                retrieveSongScoreHistory.subscribe(
                    params = RetrieveSongScoreHistory.Params.toRetrieve(idSong),
                    onSuccess = {
                        convertHistoricDatesToTimestamp(it)
                        viewState.update {
                            loading = false
                        }
                    },
                    onError = {
                        errorLiveEvent.postValue(it)
                        viewState.update {
                            loading = false
                        }
                    }
                )
            )
        }
    }

    fun getSongById() {
        viewState.update {
            loading = true
        }
        this.idSong?.let { idSong ->
            compositeDisposable?.add(
                retrieveSongById.subscribe(
                    params = RetrieveSongById.Params.toRetrieve(idSong),
                    onSuccess = {
                        songRetrievedLiveData.postValue(
                            SongViewDataWrapper(
                                it
                            )
                        )
                        viewState.update {
                            loading = false
                        }
                    },
                    onError = {
                        errorLiveEvent.postValue(it)
                        viewState.update {
                            loading = false
                        }
                    }
                )
            )
        }
    }

    fun sendSongFeedback(rateValue: String) {
        viewState.update {
            loading = true
        }
        idSong?.let { idSong ->
            compositeDisposable?.add(
                sendScoreFeedback.subscribe(
                    params = SendScoreFeedback.Params.toSend(ScoreFeedback(rateValue.toInt()), idSong),
                    onComplete = {
                        retrieveSongScoreHistory()
                        viewState.update {
                            loading = false
                        }
                    },
                    onError = {
                        errorLiveEvent.postValue(it)
                        viewState.update {
                            loading = false
                        }
                    }
                )
            )
        }
    }

    fun removeSong() {
        viewState.update {
            loading = true
        }
        idSong?.let { idSong ->
            compositeDisposable?.add(
                removeSong.subscribe(
                    params = RemoveSong.Params.toRemove(idSong),
                    onComplete = {
                        songDeletedLiveEvent.postValue(true)
                        viewState.update {
                            loading = false
                        }
                    },
                    onError = {
                        errorLiveEvent.postValue(it)
                        songDeletedLiveEvent.postValue(false)
                        viewState.update {
                            loading = false
                        }
                    }
                )
            )
        }
    }

    private fun convertHistoricDatesToTimestamp(scoreList: List<Score>) {
        val timestampKeyList = LongSparseArray<Float>()
        val dateFormat = SimpleDateFormat(DateTimeUtils.FROM_API_FORMAT, Locale.FRANCE)

        scoreList.forEach { score ->
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

        scoreHistoryRetrieved.postValue(timestampKeyListMinusReferenceTimestamp)
    }
}
package thomas.guitartrainingkotlin.presentation.viewmodel.user

import android.util.LongSparseArray
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
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

@ExperimentalCoroutinesApi
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

        idSong?.let {
            viewModelScope.launch {
                try {
                    retrieveSongScoreHistory.retrieveSongScoreHistory(it)
                        .onStart { viewState.update { loading = true } }
                        .onCompletion { viewState.update { loading = false } }
                        .collect {
                            convertHistoricDatesToTimestamp(it)
                        }
                } catch (e: Exception) {
                    errorLiveEvent.postValue(e)
                }
            }
        }
    }

    fun getSongById() {

        idSong?.let {
            viewModelScope.launch {
                try {
                    retrieveSongById.retrieveSongById(it)
                        .onStart { viewState.update { loading = true } }
                        .onCompletion { viewState.update { loading = false } }
                        .collect {
                            songRetrievedLiveData.postValue(
                                SongViewDataWrapper(
                                    it
                                )
                            )
                        }
                } catch (e: Exception) {
                    errorLiveEvent.postValue(e)
                }
            }
        }
    }

    fun sendSongFeedback(rateValue: String) {
        idSong?.let { idSong ->

            viewModelScope.launch {
                try {
                    sendScoreFeedback.sendScoreFeedback(
                        ScoreFeedback(rateValue.toInt()),
                        idSong
                    )
                        .onStart { viewState.update { loading = true } }
                        .onCompletion { viewState.update { loading = false } }
                        .collect {
                            retrieveSongScoreHistory()
                        }
                } catch (e: Exception) {
                    errorLiveEvent.postValue(e)
                }
            }
        }
    }

    fun removeSong() {
        viewState.update {
            loading = true
        }
        idSong?.let { idSong ->

            viewModelScope.launch {
                try {
                    removeSong.removeSong(idSong)
                        .onStart { viewState.update { loading = true } }
                        .onCompletion { viewState.update { loading = false } }
                        .collect {
                            songDeletedLiveEvent.postValue(true)
                        }
                } catch (e: Exception) {
                    errorLiveEvent.postValue(e)
                    songDeletedLiveEvent.postValue(false)
                }
            }
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
            timestampKeyListMinusReferenceTimestamp.put(
                key - referenceTimestamp,
                timestampKeyList.get(key)
            )
        }

        scoreHistoryRetrieved.postValue(timestampKeyListMinusReferenceTimestamp)
    }
}
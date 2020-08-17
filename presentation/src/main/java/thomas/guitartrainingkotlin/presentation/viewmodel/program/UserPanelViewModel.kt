package thomas.guitartrainingkotlin.presentation.viewmodel.program

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveInstrumentModeInSharedPrefs
import thomas.guitartrainingkotlin.domain.interactor.user.LogoutUser
import thomas.guitartrainingkotlin.domain.interactor.user.RetrieveUserById
import thomas.guitartrainingkotlin.presentation.view.datawrapper.UserViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.user.UserPanelActivityViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.AndroidStateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent

class UserPanelViewModel @ViewModelInject constructor(
    application: Application,
    /** On n'injecte pas de context directement,on préfère injecter l'objet Application. **/
    private val logoutUser: LogoutUser,
    private val retrieveUserById: RetrieveUserById,
    private val retrieveInstrumentModeInSharedPrefs: RetrieveInstrumentModeInSharedPrefs
) : AndroidStateViewModel<UserPanelActivityViewState>(application) {

    override val currentViewState =
        UserPanelActivityViewState()

    private var userId: String? = null
    private var instrumentMode: Int? = null

    val logoutSucceedLiveEvent = SingleLiveEvent<Boolean>()
    val userNotRetrievedLiveEvent = SingleLiveEvent<Boolean>()
    val userRetrievedLiveEvent = SingleLiveEvent<UserViewDataWrapper>()

    init {
        retrieveInstrumentMode()
    }

    fun logoutUser() {

        viewModelScope.launch {
            try {
                logoutUser.logoutUser()
                    .collect {
                        logoutSucceedLiveEvent.postValue(true)
                    }
            } catch (e: Exception) {
                errorLiveEvent.postValue(e)
            }
        }
    }

    fun getUserId() = userId

    fun retrieveUserId(userId: String?) {
        userId?.let {
            this.userId = it.also {
                if (it.isNotEmpty()) {
                    getUserById(it)
                } else {
                    userNotRetrievedLiveEvent.postValue(true)
                }
            }
        }
    }

    private fun retrieveInstrumentMode() {
        viewModelScope.launch {
            try {
                retrieveInstrumentModeInSharedPrefs.retrieveInstrumentModeInSharedPrefs()
                    .collect {
                        instrumentMode = it
                    }
            } catch (e: Exception) {
                errorLiveEvent.postValue(e)
            }
        }
    }

    private fun getUserById(userId: String) {

        viewModelScope.launch {
            try {
                retrieveUserById.retrieveUserById(userId)
                    .collect {
                        userRetrievedLiveEvent.postValue(
                            UserViewDataWrapper(
                                it
                            )
                        )
                    }
            } catch (e: Exception) {
                this@UserPanelViewModel.userId = null
                errorLiveEvent.postValue(e)
            }
        }
    }
}
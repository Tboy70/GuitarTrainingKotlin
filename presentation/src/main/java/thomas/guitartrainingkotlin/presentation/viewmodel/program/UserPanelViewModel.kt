package thomas.guitartrainingkotlin.presentation.viewmodel.program

import android.app.Application
import android.preference.PreferenceManager
import thomas.guitartrainingkotlin.data.manager.sharedprefs.SharedPrefsManagerImpl
import thomas.guitartrainingkotlin.domain.interactor.sharedprefs.RetrieveInstrumentModeInSharedPrefs
import thomas.guitartrainingkotlin.domain.interactor.user.LogoutUser
import thomas.guitartrainingkotlin.domain.interactor.user.RetrieveUserById
import thomas.guitartrainingkotlin.presentation.view.datawrapper.UserViewDataWrapper
import thomas.guitartrainingkotlin.presentation.view.state.user.UserPanelActivityViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.AndroidStateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import javax.inject.Inject

class UserPanelViewModel @Inject constructor(
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
        compositeDisposable?.add(
            logoutUser.subscribe(
                onComplete = {
                    logoutSucceedLiveEvent.postValue(true)
                },
                onError = {
                    errorLiveEvent.postValue(it)
                }
            )
        )
    }

    fun getUserId() = userId

    fun retrieveUserId() {
        userId = PreferenceManager.getDefaultSharedPreferences(getApplication())
            .getString(SharedPrefsManagerImpl.CURRENT_USER_ID, "0")

        userId?.let { userId ->
            if (!userId.isEmpty() && userId != "0") {
                getUserById(userId)
            } else {
                userNotRetrievedLiveEvent.postValue(true)
            }
        }
    }

    private fun retrieveInstrumentMode() {
        compositeDisposable?.add(
            retrieveInstrumentModeInSharedPrefs.subscribe(
                onSuccess = {
                    instrumentMode = it
                },
                onError = {
                    errorLiveEvent.postValue(it)
                }
            )
        )
    }

    private fun getUserById(userId: String) {
        compositeDisposable?.add(
            retrieveUserById.subscribe(
                params = RetrieveUserById.Params.toRetrieve(userId),
                onSuccess = {
                    userRetrievedLiveEvent.postValue(
                        UserViewDataWrapper(
                            it
                        )
                    )
                },
                onError = {
                    this.userId = null
                    errorLiveEvent.postValue(it)
                }
            )
        )
    }
}
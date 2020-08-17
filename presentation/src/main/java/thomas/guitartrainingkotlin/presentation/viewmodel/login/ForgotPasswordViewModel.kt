package thomas.guitartrainingkotlin.presentation.viewmodel.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import thomas.guitartrainingkotlin.domain.interactor.user.RetrievePassword
import thomas.guitartrainingkotlin.presentation.view.state.user.ForgotPasswordFragmentViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent

@ExperimentalCoroutinesApi
class ForgotPasswordViewModel @ViewModelInject constructor(
    private val retrievePassword: RetrievePassword
) : StateViewModel<ForgotPasswordFragmentViewState>() {

    override val currentViewState = ForgotPasswordFragmentViewState()

    val emailSentLiveEvent = SingleLiveEvent<Boolean>()

    fun retrievePassword(emailAddress: String) {

        viewModelScope.launch {
            try {
                retrievePassword.retrievePassword(emailAddress)
                    .onStart { viewState.update { loading = true } }
                    .onCompletion { viewState.update { loading = false } }
                    .collect {
                        emailSentLiveEvent.postValue(true)
                    }
            } catch (e: Exception) {
                errorLiveEvent.postValue(e)
            }
        }
    }
}
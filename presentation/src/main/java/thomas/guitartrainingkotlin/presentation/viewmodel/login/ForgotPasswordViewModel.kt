package thomas.guitartrainingkotlin.presentation.viewmodel.login

import thomas.guitartrainingkotlin.domain.interactor.user.RetrievePassword
import thomas.guitartrainingkotlin.presentation.view.state.user.ForgotPasswordFragmentViewState
import thomas.guitartrainingkotlin.presentation.viewmodel.base.StateViewModel
import thomas.guitartrainingkotlin.presentation.viewmodel.livedata.SingleLiveEvent
import javax.inject.Inject

class ForgotPasswordViewModel @Inject constructor(
    private val retrievePassword: RetrievePassword
) : StateViewModel<ForgotPasswordFragmentViewState>() {

    override val currentViewState = ForgotPasswordFragmentViewState()

    val emailSentLiveEvent = SingleLiveEvent<Boolean>()

    fun retrievePassword(emailAddress: String) {
        retrievePassword.subscribe(
            onComplete = {
                emailSentLiveEvent.postValue(true)
            },
            onError = {
                errorLiveEvent.postValue(it)
            },
            params = RetrievePassword.Params.toRetrieve(emailAddress)
        )
    }
}
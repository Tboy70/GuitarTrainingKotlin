package thomas.example.com.guitarTrainingKotlin.viewmodel.program

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import thomas.example.com.interactor.user.LogoutUser
import javax.inject.Inject

class UserPanelViewModel @Inject constructor(private val logoutUser: LogoutUser) : ViewModel() {

    val finishLoading: MutableLiveData<Boolean> = MutableLiveData()
    val logoutSucceed: MutableLiveData<Boolean> = MutableLiveData()

    fun logoutUser() {

        logoutUser.execute(
                onComplete = {
                    finishLoading.postValue(true)
                },
                onError = {
                },
                onNext = {
                    logoutSucceed.postValue(true)

                }, params = Unit)
    }
}
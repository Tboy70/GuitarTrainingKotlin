package thomas.example.com.interactor.user

import io.reactivex.Observable
import thomas.example.com.executor.ThreadExecutor
import thomas.example.com.interactor.UseCase
import thomas.example.com.model.User
import thomas.example.com.repository.UserRepository
import javax.inject.Inject

class CreateNewUser @Inject constructor(
    threadExecutor: ThreadExecutor,
    private var userRepository: UserRepository
) : UseCase<String, CreateNewUser.Params>(threadExecutor) {

    override fun buildUseCaseObservable(params: Params): Observable<String> {
        return userRepository.createNewUser(params.user)
    }

    class Params(val user: User) {

        companion object {
            fun toCreate(user: User): Params {
                return Params(user)
            }
        }
    }
}
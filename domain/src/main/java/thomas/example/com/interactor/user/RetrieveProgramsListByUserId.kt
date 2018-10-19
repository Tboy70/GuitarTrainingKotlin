package thomas.example.com.interactor.user

import io.reactivex.Single
import thomas.example.com.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.example.com.model.Program
import thomas.example.com.repository.ProgramRepository
import javax.inject.Inject

class RetrieveProgramsListByUserId @Inject constructor(
        private var programRepository: ProgramRepository
) : SingleParametrizedUseCase<List<Program>, RetrieveProgramsListByUserId.Params>() {

    override fun build(params: Params): Single<List<Program>> {
        return programRepository.retrieveProgramsListByUserId(params.idUser)
    }

    class Params(val idUser: String) {

        companion object {
            fun forList(idUser: String): Params {
                return Params(idUser)
            }
        }
    }
}
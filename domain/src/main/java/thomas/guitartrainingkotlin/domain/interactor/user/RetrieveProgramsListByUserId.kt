package thomas.guitartrainingkotlin.domain.interactor.user

import io.reactivex.Single
import thomas.guitartrainingkotlin.domain.interactor.base.parametrized.SingleParametrizedUseCase
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import javax.inject.Inject

class RetrieveProgramsListByUserId @Inject constructor(
        private val programRepository: ProgramRepository
) : SingleParametrizedUseCase<List<Program>, RetrieveProgramsListByUserId.Params>() {

    override fun build(params: Params): Single<List<Program>> {
        return programRepository.retrieveProgramListByUserId(params.userId)
    }

    class Params(val userId: String) {

        companion object {
            fun forList(userId: String): Params {
                return Params(userId)
            }
        }
    }
}
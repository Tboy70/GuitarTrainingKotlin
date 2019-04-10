package thomas.guitartrainingkotlin.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import thomas.guitartrainingkotlin.data.business.APIBusinessHelper
import thomas.guitartrainingkotlin.data.mapper.ExerciseEntityDataMapper
import thomas.guitartrainingkotlin.data.mapper.ProgramEntityDataMapper
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramDataRepository @Inject constructor(
    private val apiBusinessHelper: APIBusinessHelper,
    private val programEntityDataMapper: ProgramEntityDataMapper,
    private val exerciseEntityDataMapper: ExerciseEntityDataMapper
) : ProgramRepository {

    override fun retrieveProgramListByUserId(userId: String): Single<List<Program>> {
        return Single.defer {
            apiBusinessHelper.retrieveProgramListByUserId(userId).map {
                programEntityDataMapper.transformFromEntity(it)
            }
        }
    }

    override fun retrieveProgramById(idProgram: String): Single<Program> {
        return Single.defer {
            apiBusinessHelper.retrieveProgramFromId(idProgram).map {
                programEntityDataMapper.transformFromEntity(it)
            }
        }
    }

    override fun createProgram(program: Program, exercisesList: List<Exercise>): Completable {
        return Completable.defer {
            apiBusinessHelper.createProgram(programEntityDataMapper.transformToEntity(program)).map {
                for (exercise in exercisesList) {
                    exercise.idProgram = it
                }
            }.flatMapCompletable {
                apiBusinessHelper.createExercise(exerciseEntityDataMapper.transformToEntity(exercisesList))
            }
        }
    }

    override fun updateProgram(program: Program, exercisesToBeRemoved: List<Exercise>): Completable {
        return Completable.defer {
            apiBusinessHelper.updateProgram(
                programEntityDataMapper.transformToEntity(program),
                exerciseEntityDataMapper.transformToEntity(exercisesToBeRemoved)
            )
        }
    }

    override fun removeProgram(idProgram: String): Completable {
        return Completable.defer {
            apiBusinessHelper.removeProgram(idProgram)
        }
    }
}
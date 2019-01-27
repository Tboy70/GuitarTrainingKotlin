package thomas.example.com.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import thomas.example.com.data.mapper.ExerciseEntityDataMapper
import thomas.example.com.data.mapper.ProgramEntityDataMapper
import thomas.example.com.data.business.APIBusinessHelper
import thomas.example.com.model.Exercise
import thomas.example.com.model.Program
import thomas.example.com.repository.ProgramRepository
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
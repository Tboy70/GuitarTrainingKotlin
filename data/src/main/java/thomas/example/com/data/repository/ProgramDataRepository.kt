package thomas.example.com.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import thomas.example.com.data.mapper.ExerciseEntityDataMapper
import thomas.example.com.data.mapper.ProgramEntityDataMapper
import thomas.example.com.data.business.APIBusinessHelper
import thomas.example.com.data.business.ProgramBusinessHelper
import thomas.example.com.model.Exercise
import thomas.example.com.model.Program
import thomas.example.com.repository.ProgramRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramDataRepository @Inject constructor(
    private val programBusinessHelper: ProgramBusinessHelper,
    private val apiBusinessHelper: APIBusinessHelper,
    private val programEntityDataMapper: ProgramEntityDataMapper,
    private val exerciseEntityDataMapper: ExerciseEntityDataMapper
) : ProgramRepository {

    override fun retrieveProgramsListByUserId(idUser: String): Single<List<Program>> {
        return Single.defer {
            apiBusinessHelper.retrieveProgramsListByUserId(idUser).map {
                programEntityDataMapper.transformListEntitiesToListModels(it)
            }
        }
    }

    override fun retrieveProgramById(idProgram: String): Single<Program> {
        return Single.defer {
            apiBusinessHelper.retrieveProgramFromId(idProgram).map {
                programEntityDataMapper.transformEntityToModel(it)
            }
        }
    }

    override fun createProgram(program: Program, exercisesList: List<Exercise>): Completable {
        return Completable.defer {
            apiBusinessHelper.createProgram(programEntityDataMapper.transformModelToEntity(program)).map {
                for (exercise in exercisesList) {
                    exercise.idProgram = it
                }
            }.flatMapCompletable {
                apiBusinessHelper.createExercise(exerciseEntityDataMapper.transformListModelsToListEntities(exercisesList))
            }
        }
    }

    override fun updateProgram(program: Program, exercisesToBeRemoved: List<Exercise>): Completable {
        return Completable.defer {
            apiBusinessHelper.updateProgram(
                programEntityDataMapper.transformModelToEntity(program),
                exerciseEntityDataMapper.transformListModelsToListEntities(exercisesToBeRemoved)
            )
        }
    }

    override fun removeProgram(idProgram: String): Completable {
        return Completable.defer {
            apiBusinessHelper.removeProgram(idProgram)
        }
    }
}
package thomas.example.com.data.repository

import io.reactivex.Observable
import thomas.example.com.data.mapper.ExerciseEntityDataMapper
import thomas.example.com.data.mapper.ProgramEntityDataMapper
import thomas.example.com.data.repository.client.APIClient
import thomas.example.com.data.repository.client.ProgramClient
import thomas.example.com.model.Exercise
import thomas.example.com.model.Program
import thomas.example.com.repository.ProgramRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramRepositoryImpl @Inject constructor(private val programClient: ProgramClient,
                                                private val apiClient: APIClient,
                                                private val programEntityDataMapper: ProgramEntityDataMapper,
                                                private val exerciseEntityDataMapper: ExerciseEntityDataMapper) : ProgramRepository {

    override fun retrieveProgramsListByUserId(idUser: String): Observable<List<Program>> {
        return Observable.defer {
            apiClient.retrieveProgramsListByUserId(idUser).map {
                programEntityDataMapper.transformListEntitiesToListModels(it)
            }
        }
    }

    override fun retrieveProgramById(idProgram: String): Observable<Program> {
        return Observable.defer {
            apiClient.retrieveProgramFromId(idProgram).map {
                programEntityDataMapper.transformEntityToModel(it)
            }
        }
    }

    override fun createProgram(program: Program, exercisesList: List<Exercise>): Observable<Boolean> {
        return Observable.defer {
            apiClient.createProgram(programEntityDataMapper.transformModelToEntity(program)).map {
                for (exercise in exercisesList) {
                    exercise.idProgram = it
                }
            }.flatMap {
                apiClient.createExercise(exerciseEntityDataMapper.transformListModelsToListEntities(exercisesList))
            }
        }
    }

    override fun updateProgram(program: Program, exercisesToBeRemoved: List<Exercise>): Observable<Boolean> {
        return Observable.defer {
            apiClient.updateProgram(programEntityDataMapper.transformModelToEntity(program), exerciseEntityDataMapper.transformListModelsToListEntities(exercisesToBeRemoved))
        }
    }

    override fun removeProgram(idProgram: String): Observable<Boolean> {
        return Observable.defer {
            apiClient.removeProgram(idProgram)
        }
    }
}
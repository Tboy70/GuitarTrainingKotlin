package thomas.guitartrainingkotlin.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import thomas.guitartrainingkotlin.data.business.APIBusinessHelper
import thomas.guitartrainingkotlin.data.mapper.ExerciseEntityDataMapper
import thomas.guitartrainingkotlin.data.mapper.ProgramEntityDataMapper
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.domain.model.Program
import thomas.guitartrainingkotlin.domain.repository.ProgramRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@FlowPreview
@ExperimentalCoroutinesApi
class ProgramDataRepository @Inject constructor(
    private val apiBusinessHelper: APIBusinessHelper,
    private val programEntityDataMapper: ProgramEntityDataMapper,
    private val exerciseEntityDataMapper: ExerciseEntityDataMapper
) : ProgramRepository {

    override fun retrieveProgramListByUserId(userId: String): Flow<List<Program>> {
        return apiBusinessHelper.retrieveProgramListByUserId(userId).map {
            programEntityDataMapper.transformFromEntity(it)
        }
    }

    override fun retrieveProgramById(idProgram: String): Flow<Program> {
        return apiBusinessHelper.retrieveProgramFromId(idProgram).map {
            programEntityDataMapper.transformFromEntity(it)
        }
    }

    override fun createProgram(program: Program, exercisesList: List<Exercise>): Flow<Unit> {
        return apiBusinessHelper.createProgram(programEntityDataMapper.transformToEntity(program))
            .mapNotNull { idProgram ->
                for (exercise in exercisesList) {
                    exercise.idProgram = idProgram
                }
            }.flatMapConcat {
                apiBusinessHelper.createExercise(
                    exerciseEntityDataMapper.transformToEntity(
                        exercisesList
                    )
                )
            }
    }

    override fun updateProgram(
        program: Program,
        exercisesToBeRemoved: List<Exercise>
    ): Flow<Unit> {
        return apiBusinessHelper.updateProgram(
            programEntityDataMapper.transformToEntity(program),
            exerciseEntityDataMapper.transformToEntity(exercisesToBeRemoved)
        )
    }

    override fun removeProgram(idProgram: String): Flow<Unit> {
        return apiBusinessHelper.removeProgram(idProgram)
    }
}
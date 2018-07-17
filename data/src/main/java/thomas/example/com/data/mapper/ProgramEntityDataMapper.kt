package thomas.example.com.data.mapper

import thomas.example.com.data.entity.ProgramEntity
import thomas.example.com.model.Program
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramEntityDataMapper @Inject constructor(private val exerciseEntityDataMapper: ExerciseEntityDataMapper) {

    fun transformProgramEntityToProgramModel(programEntity: ProgramEntity): Program {
        val program = Program()
        program.idProgram = programEntity.idProgram
        program.nameProgram = programEntity.nameProgram
        program.descriptionProgram = programEntity.descriptionProgram
        program.defaultProgram = programEntity.defaultProgram
        program.idUser = programEntity.idUser
        program.exercises = exerciseEntityDataMapper.transformListExerciseEntitiesToExerciseModels(programEntity.exerciseEntities)
        return program
    }

    fun transformListProgramEntitiesToProgramModels(programEntityList: List<ProgramEntity>): List<Program> {
        val programList: MutableList<Program> = mutableListOf()

        for (programEntity: ProgramEntity in programEntityList) {
            programList.add(transformProgramEntityToProgramModel(programEntity))
        }
        return programList
    }

    fun transformProgramToProgramEntity(program: Program): ProgramEntity {
        val programEntity = ProgramEntity()
        programEntity.idProgram = program.idProgram
        programEntity.nameProgram = program.nameProgram
        programEntity.descriptionProgram = program.descriptionProgram
        programEntity.defaultProgram = program.defaultProgram
        programEntity.idUser = program.idUser
        programEntity.exerciseEntities = exerciseEntityDataMapper.transformListExerciseModelsToExerciseEntites(program.exercises)
        return programEntity
    }

    fun transformListProgramModelsToProgramEntities(programModelsList: List<Program>): List<ProgramEntity> {
        val programEntitiesList: MutableList<ProgramEntity> = mutableListOf()

        for (program: Program in programModelsList) {
            programEntitiesList.add(transformProgramToProgramEntity(program))
        }
        return programEntitiesList
    }

}
package thomas.example.com.data.mapper

import thomas.example.com.data.entity.ProgramEntity
import thomas.example.com.model.Program
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramEntityDataMapper @Inject constructor(private val exerciseEntityDataMapper: ExerciseEntityDataMapper) {

    fun transformEntityToModel(programEntity: ProgramEntity): Program {
        val program = Program()
        program.idProgram = programEntity.idProgram
        program.nameProgram = programEntity.nameProgram
        program.descriptionProgram = programEntity.descriptionProgram
        program.defaultProgram = programEntity.defaultProgram
        program.idUser = programEntity.idUser
        program.exercises = exerciseEntityDataMapper.transformListEntitiesToListModels(programEntity.exerciseEntities)
        return program
    }

    fun transformListEntitiesToListModels(programEntityList: List<ProgramEntity>): List<Program> {
        val programList: MutableList<Program> = mutableListOf()

        for (programEntity: ProgramEntity in programEntityList) {
            programList.add(transformEntityToModel(programEntity))
        }
        return programList
    }

    fun transformModelToEntity(program: Program): ProgramEntity {
        val programEntity = ProgramEntity()
        programEntity.idProgram = program.idProgram
        programEntity.nameProgram = program.nameProgram
        programEntity.descriptionProgram = program.descriptionProgram
        programEntity.defaultProgram = program.defaultProgram
        programEntity.idUser = program.idUser
        programEntity.exerciseEntities = exerciseEntityDataMapper.transformListModelsToListEntities(program.exercises)
        return programEntity
    }

    fun transformListModelsToListEntities(programModelsList: List<Program>): List<ProgramEntity> {
        val programEntitiesList: MutableList<ProgramEntity> = mutableListOf()

        for (program: Program in programModelsList) {
            programEntitiesList.add(transformModelToEntity(program))
        }
        return programEntitiesList
    }
}
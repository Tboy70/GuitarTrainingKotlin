package thomas.example.com.data.mapper.remote

import thomas.example.com.data.entity.ProgramEntity
import thomas.example.com.data.entity.remote.ProgramRemoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramRemoteEntityDataMapper @Inject constructor(private val exerciseRemoteEntityDataMapper: ExerciseRemoteEntityDataMapper) {

    fun transformProgramRemoteEntityToProgramEntity(programRemoteEntity: ProgramRemoteEntity): ProgramEntity {
        val programEntity = ProgramEntity()
        programEntity.idProgram = programRemoteEntity.idProgram
        programEntity.nameProgram = programRemoteEntity.nameProgram
        programEntity.descriptionProgram = programRemoteEntity.descriptionProgram
        programEntity.defaultProgram = programRemoteEntity.defaultProgram
        programEntity.idUser = programRemoteEntity.idUser
        programEntity.exerciseEntities = exerciseRemoteEntityDataMapper.transformListExerciseRemoteEntitiesToListExerciseEntities(programRemoteEntity.exerciseRemoteEntities)

        return programEntity
    }

    fun transformListProgramRemoteEntitiesToListProgramEntities(programRemoteEntitiesList: List<ProgramRemoteEntity>): List<ProgramEntity> {
        val programEntitiesList: MutableList<ProgramEntity> = mutableListOf()

        for (programRemoteEntity: ProgramRemoteEntity in programRemoteEntitiesList) {
            programEntitiesList.add(transformProgramRemoteEntityToProgramEntity(programRemoteEntity))
        }
        return programEntitiesList
    }

    fun transformProgramEntityToProgramRemoteEntity(programEntity: ProgramEntity): ProgramRemoteEntity {
        val programRemoteEntity = ProgramRemoteEntity()
        programRemoteEntity.idProgram = programEntity.idProgram
        programRemoteEntity.nameProgram = programEntity.nameProgram
        programRemoteEntity.descriptionProgram = programEntity.descriptionProgram
        programRemoteEntity.defaultProgram = programEntity.defaultProgram
        programRemoteEntity.idUser = programEntity.idUser
        programRemoteEntity.exerciseRemoteEntities = exerciseRemoteEntityDataMapper
                .transformListExerciseEntitiesToListExerciseRemoteEntities(programEntity.exerciseEntities)

        return programRemoteEntity
    }

    fun transformListProgramEntitiesToListProgramRemoteEntities(programEntitiesList: List<ProgramEntity>): List<ProgramRemoteEntity> {
        val programRemoteEntitiesList: MutableList<ProgramRemoteEntity> = mutableListOf()

        for (programEntity: ProgramEntity in programEntitiesList) {
            programRemoteEntitiesList.add(transformProgramEntityToProgramRemoteEntity(programEntity))
        }
        return programRemoteEntitiesList
    }

}
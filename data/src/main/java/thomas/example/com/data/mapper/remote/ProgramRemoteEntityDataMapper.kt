package thomas.example.com.data.mapper.remote

import thomas.example.com.data.entity.ProgramEntity
import thomas.example.com.data.entity.remote.program.ProgramRemoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramRemoteEntityDataMapper @Inject constructor(private val exerciseRemoteEntityDataMapper: ExerciseRemoteEntityDataMapper) {

    fun transformRemoteEntityToEntity(programRemoteEntity: ProgramRemoteEntity): ProgramEntity {
        val programEntity = ProgramEntity()
        programEntity.idProgram = programRemoteEntity.idProgram
        programEntity.nameProgram = programRemoteEntity.nameProgram
        programEntity.descriptionProgram = programRemoteEntity.descriptionProgram
        programEntity.defaultProgram = programRemoteEntity.defaultProgram
        programEntity.idUser = programRemoteEntity.idUser
        programEntity.exerciseEntities = exerciseRemoteEntityDataMapper.transformListRemoteEntitiesToListEntities(programRemoteEntity.exerciseRemoteEntities)
        programEntity.idInstrument = programRemoteEntity.idInstrument

        return programEntity
    }

    fun transformListRemoteEntitiesToListEntities(programRemoteEntitiesList: List<ProgramRemoteEntity>): List<ProgramEntity> {
        val programEntitiesList: MutableList<ProgramEntity> = mutableListOf()

        for (programRemoteEntity: ProgramRemoteEntity in programRemoteEntitiesList) {
            programEntitiesList.add(transformRemoteEntityToEntity(programRemoteEntity))
        }
        return programEntitiesList
    }

    fun transformEntityToRemoteEntity(programEntity: ProgramEntity): ProgramRemoteEntity {
        val programRemoteEntity = ProgramRemoteEntity()
        programRemoteEntity.idProgram = programEntity.idProgram
        programRemoteEntity.nameProgram = programEntity.nameProgram
        programRemoteEntity.descriptionProgram = programEntity.descriptionProgram
        programRemoteEntity.defaultProgram = programEntity.defaultProgram
        programRemoteEntity.idUser = programEntity.idUser
        programRemoteEntity.exerciseRemoteEntities = exerciseRemoteEntityDataMapper.transformListEntitiesToListRemoteEntities(programEntity.exerciseEntities)
        programRemoteEntity.idInstrument = programEntity.idInstrument

        return programRemoteEntity
    }

    fun transformListEntitiesToListRemoteEntities(programEntitiesList: List<ProgramEntity>): List<ProgramRemoteEntity> {
        val programRemoteEntitiesList: MutableList<ProgramRemoteEntity> = mutableListOf()

        for (programEntity: ProgramEntity in programEntitiesList) {
            programRemoteEntitiesList.add(transformEntityToRemoteEntity(programEntity))
        }
        return programRemoteEntitiesList
    }

}
package thomas.guitartrainingkotlin.data.mapper.remote

import thomas.guitartrainingkotlin.data.entity.ProgramEntity
import thomas.guitartrainingkotlin.data.entity.remote.program.ProgramRemoteEntity
import thomas.guitartrainingkotlin.data.exception.mapper.DataMappingException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramRemoteEntityDataMapper @Inject constructor(
    private val exerciseRemoteEntityDataMapper: ExerciseRemoteEntityDataMapper
) {

    @Throws(DataMappingException::class)
    fun transformToEntity(programRemoteEntity: ProgramRemoteEntity): ProgramEntity {
        try {
            return ProgramEntity(
                idProgram = programRemoteEntity.idProgram,
                nameProgram = programRemoteEntity.nameProgram,
                descriptionProgram = programRemoteEntity.descriptionProgram,
                defaultProgram = programRemoteEntity.defaultProgram,
                userId = programRemoteEntity.userId,
                exerciseEntityList = exerciseRemoteEntityDataMapper.transformToEntity(programRemoteEntity.exerciseRemoteEntityList),
                idInstrument = programRemoteEntity.idInstrument
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToEntity(programRemoteEntityList: List<ProgramRemoteEntity>) =
        programRemoteEntityList.mapNotNull { programRemoteEntity ->
            try {
                transformToEntity(programRemoteEntity)
            } catch (e: DataMappingException) {
                null
            }
        }

    @Throws(DataMappingException::class)
    fun transformFromEntity(programEntity: ProgramEntity): ProgramRemoteEntity {
        try {
            return ProgramRemoteEntity(
                idProgram = programEntity.idProgram,
                nameProgram = programEntity.nameProgram,
                descriptionProgram = programEntity.descriptionProgram,
                defaultProgram = programEntity.defaultProgram,
                userId = programEntity.userId,
                exerciseRemoteEntityList = exerciseRemoteEntityDataMapper.transformFromEntity(programEntity.exerciseEntityList),
                idInstrument = programEntity.idInstrument
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformFromEntity(programEntityList: List<ProgramEntity>) = programEntityList.mapNotNull { programEntity ->
        try {
            transformFromEntity(programEntity)
        } catch (e: DataMappingException) {
            null
        }
    }
}
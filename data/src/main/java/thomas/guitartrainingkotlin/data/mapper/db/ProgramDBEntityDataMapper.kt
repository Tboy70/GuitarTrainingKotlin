package thomas.guitartrainingkotlin.data.mapper.db

import thomas.guitartrainingkotlin.data.db.entity.ProgramDBEntity
import thomas.guitartrainingkotlin.data.entity.ProgramEntity
import thomas.guitartrainingkotlin.data.exception.mapper.DataMappingException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramDBEntityDataMapper @Inject constructor(
    private val exerciseDBEntityDataMapper: ExerciseDBEntityDataMapper
) {

    fun transformFromDB(programDBEntityList: List<ProgramDBEntity>) =
        programDBEntityList.mapNotNull { programDBEntity ->
            try {
                transformFromDB(programDBEntity)
            } catch (e: DataMappingException) {
                null
            }
        }

    @Throws(DataMappingException::class)
    fun transformFromDB(programDBEntity: ProgramDBEntity): ProgramEntity {
        try {
            return ProgramEntity(
                userId = programDBEntity.userId,
                idProgram = programDBEntity.idProgram,
                nameProgram = programDBEntity.nameProgram,
                idInstrument = programDBEntity.idInstrument,
                defaultProgram = programDBEntity.defaultProgram,
                descriptionProgram = programDBEntity.descriptionProgram,
                exerciseEntityList = programDBEntity.exerciseList?.let { exerciseDBEntityDataMapper.transformFromDB(it) }
                    ?: emptyList()
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToDB(programEntityList: List<ProgramEntity>) = programEntityList.mapNotNull { programEntity ->
        try {
            transformToDB(programEntity)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformToDB(programEntity: ProgramEntity): ProgramDBEntity {
        try {
            return ProgramDBEntity(
                userId = programEntity.userId,
                idProgram = programEntity.idProgram,
                nameProgram = programEntity.nameProgram,
                idInstrument = programEntity.idInstrument,
                defaultProgram = programEntity.defaultProgram,
                descriptionProgram = programEntity.descriptionProgram,
                exerciseList = exerciseDBEntityDataMapper.transformToDB(programEntity.exerciseEntityList)
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }
}
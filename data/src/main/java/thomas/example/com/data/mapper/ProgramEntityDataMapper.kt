package thomas.example.com.data.mapper

import thomas.example.com.data.entity.ProgramEntity
import thomas.example.com.data.exception.mapper.DataMappingException
import thomas.example.com.model.Program
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramEntityDataMapper @Inject constructor(private val exerciseEntityDataMapper: ExerciseEntityDataMapper) {

    fun transformFromEntity(programEntityList: List<ProgramEntity>) = programEntityList.mapNotNull { programEntity ->
        try {
            transformFromEntity(programEntity)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformFromEntity(programEntity: ProgramEntity): Program {
        try {
            return Program(
                idProgram = programEntity.idProgram,
                nameProgram = programEntity.nameProgram,
                descriptionProgram = programEntity.descriptionProgram,
                defaultProgram = programEntity.defaultProgram,
                userId = programEntity.userId,
                exercises = exerciseEntityDataMapper.transformFromEntity(programEntity.exerciseEntityList).toMutableList(),
                idInstrument = programEntity.idInstrument
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToEntity(programList: List<Program>) = programList.mapNotNull { program ->
        try {
            transformToEntity(program)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformToEntity(program: Program): ProgramEntity {
        try {
            return ProgramEntity(
                idProgram = program.idProgram,
                nameProgram = program.nameProgram,
                descriptionProgram = program.descriptionProgram,
                defaultProgram = program.defaultProgram,
                userId = program.userId,
                exerciseEntityList = exerciseEntityDataMapper.transformToEntity(program.exercises),
                idInstrument = program.idInstrument
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }
}
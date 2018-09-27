package thomas.example.com.data.repository.client

import io.reactivex.Observable
import thomas.example.com.data.entity.ExerciseEntity
import thomas.example.com.data.entity.ProgramEntity
import thomas.example.com.data.entity.SongEntity
import thomas.example.com.data.entity.UserEntity
import thomas.example.com.data.mapper.remote.ExerciseRemoteEntityDataMapper
import thomas.example.com.data.mapper.remote.ProgramRemoteEntityDataMapper
import thomas.example.com.data.mapper.remote.SongRemoteEntityDataMapper
import thomas.example.com.data.mapper.remote.UserRemoteEntityDataMapper
import thomas.example.com.data.module.ApiModule
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.data.utils.InstrumentModeUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class APIClient @Inject constructor(private val apiModule: ApiModule,
                                    private val moduleSharedPrefsImpl: ModuleSharedPrefsImpl,
                                    private val programRemoteEntityDataMapper: ProgramRemoteEntityDataMapper,
                                    private val exerciseRemoteEntityDataMapper: ExerciseRemoteEntityDataMapper,
                                    private val userRemoteEntityDataMapper: UserRemoteEntityDataMapper,
                                    private val songRemoteEntityDataMapper: SongRemoteEntityDataMapper) {

    fun connectUser(userEntity: UserEntity): Observable<UserEntity>? {
        return apiModule.connectUser(userRemoteEntityDataMapper.transformEntityToRemoteEntity(userEntity))
                .map {
                    userRemoteEntityDataMapper.transformRemoteEntityToEntity(it)
                }
    }

    fun retrieveProgramsListByUserId(idUser: String): Observable<List<ProgramEntity>> {
        return apiModule.retrieveProgramsListByUserId(idUser, InstrumentModeUtils.getIntValueFromInstrumentMode(moduleSharedPrefsImpl.getInstrumentModeInSharedPrefs())).map {
            programRemoteEntityDataMapper.transformListRemoteEntitiesToListEntities(it)
        }
    }

    fun retrieveProgramFromId(idProgram: String): Observable<ProgramEntity> {
        return apiModule.retrieveProgramFromId(idProgram).map {
            programRemoteEntityDataMapper.transformRemoteEntityToEntity(it)
        }
    }

    fun createProgram(programEntity: ProgramEntity): Observable<String> {
        return apiModule.createProgram(programRemoteEntityDataMapper.transformEntityToRemoteEntity(programEntity)).map {
            it
        }
    }

    fun createExercise(listExercisesEntities: List<ExerciseEntity>): Observable<Boolean> {
        return apiModule.createExercise(exerciseRemoteEntityDataMapper.transformListEntitiesToListRemoteEntities(listExercisesEntities))
    }

    fun updateProgram(programEntity: ProgramEntity, exerciseEntityList: List<ExerciseEntity>): Observable<Boolean> {
        return apiModule.removeExercises(exerciseRemoteEntityDataMapper.transformListEntitiesToListRemoteEntities(exerciseEntityList))
                .concatWith(apiModule.updateProgram(programRemoteEntityDataMapper.transformEntityToRemoteEntity(programEntity)))
                .concatWith(apiModule.updateExercise(exerciseRemoteEntityDataMapper.transformListEntitiesToListRemoteEntities(programEntity.exerciseEntities)))
    }

    fun removeProgram(idProgram: String): Observable<Boolean> {
        return apiModule.removeProgram(idProgram)
    }

    fun retrieveUserById(idUser: String): Observable<UserEntity> {
        return apiModule.retrieveUserById(idUser).map {
            userRemoteEntityDataMapper.transformRemoteEntityToEntity(it)
        }
    }

    fun createNewUser(userEntity: UserEntity): Observable<String> {
        return apiModule.createNewUser(userRemoteEntityDataMapper.transformEntityToRemoteEntity(userEntity)).map {
            it
        }

    }

    fun createSong(songEntity: SongEntity): Observable<String> {
        return apiModule.createSong(songRemoteEntityDataMapper.transformEntityToRemoteEntity(songEntity)).map {
            it
        }
    }

    fun retrieveSongsListByUserId(idUser: String): Observable<List<SongEntity>> {
        return apiModule.retrieveSongsListByUserId(idUser).map {
            songRemoteEntityDataMapper.transformListRemoteEntitiesToListEntities(it)
        }
    }
}
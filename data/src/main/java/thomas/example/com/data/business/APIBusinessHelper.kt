package thomas.example.com.data.business

import io.reactivex.Completable
import io.reactivex.Single
import thomas.example.com.data.entity.*
import thomas.example.com.data.mapper.remote.*
import thomas.example.com.data.manager.ApiManager
import thomas.example.com.data.manager.SharedPrefsManager
import thomas.example.com.data.utils.InstrumentModeUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class APIBusinessHelper @Inject constructor(
    private val apiManager: ApiManager,
    private val sharedPrefsManager: SharedPrefsManager,
    private val programRemoteEntityDataMapper: ProgramRemoteEntityDataMapper,
    private val exerciseRemoteEntityDataMapper: ExerciseRemoteEntityDataMapper,
    private val userRemoteEntityDataMapper: UserRemoteEntityDataMapper,
    private val songRemoteEntityDataMapper: SongRemoteEntityDataMapper,
    private val scoreFeedbackRemoteEntityDataMapper: ScoreFeedbackRemoteEntityDataMapper,
    private val scoreRemoteEntityDataMapper: ScoreRemoteEntityDataMapper
) {

    fun connectUser(userEntity: UserEntity): Single<UserEntity> {
        return apiManager.connectUser(userRemoteEntityDataMapper.transformEntityToRemoteEntity(userEntity))
            .map {
                userRemoteEntityDataMapper.transformRemoteEntityToEntity(it)
            }
    }

    fun retrieveUserById(idUser: String): Single<UserEntity> {
        return apiManager.retrieveUserById(idUser).map {
            userRemoteEntityDataMapper.transformRemoteEntityToEntity(it)
        }
    }

    fun createNewUser(userEntity: UserEntity): Completable {
        return apiManager.createNewUser(userRemoteEntityDataMapper.transformEntityToRemoteEntity(userEntity))
    }

    fun suppressAccount(idUser: String): Completable {
        return apiManager.suppressAccount(idUser)
    }

    fun retrieveProgramsListByUserId(idUser: String): Single<List<ProgramEntity>> {
        return apiManager.retrieveProgramsListByUserId(
                idUser,
                InstrumentModeUtils.getIntValueFromInstrumentMode(sharedPrefsManager.getInstrumentModeInSharedPrefs())
        ).map {
            programRemoteEntityDataMapper.transformListRemoteEntitiesToListEntities(it)
        }
    }

    fun retrieveProgramFromId(idProgram: String): Single<ProgramEntity> {
        return apiManager.retrieveProgramFromId(idProgram).map {
            programRemoteEntityDataMapper.transformRemoteEntityToEntity(it)
        }
    }

    fun createProgram(programEntity: ProgramEntity): Single<String> {
        return apiManager.createProgram(programRemoteEntityDataMapper.transformEntityToRemoteEntity(programEntity)).map {
            it
        }
    }

    fun createExercise(listExercisesEntities: List<ExerciseEntity>): Completable {
        return apiManager.createExercise(
                exerciseRemoteEntityDataMapper.transformListEntitiesToListRemoteEntities(
                        listExercisesEntities
                )
        )
    }

    fun updateProgram(programEntity: ProgramEntity, exerciseEntityList: List<ExerciseEntity>): Completable {
        return apiManager.removeExercises(
                exerciseRemoteEntityDataMapper.transformListEntitiesToListRemoteEntities(
                        exerciseEntityList
                )
        ).concatWith(
                apiManager.updateProgram(
                        programRemoteEntityDataMapper.transformEntityToRemoteEntity(
                                programEntity
                        )
                )
        ).concatWith(
                apiManager.updateExercise(
                        exerciseRemoteEntityDataMapper.transformListEntitiesToListRemoteEntities(
                                programEntity.exerciseEntities
                        )
                )
        )
    }

    fun removeProgram(idProgram: String): Completable {
        return apiManager.removeProgram(idProgram)
    }

    fun retrieveSongsListByUserId(idUser: String): Single<List<SongEntity>> {
        return apiManager.retrieveSongsListByUserId(
                idUser,
                InstrumentModeUtils.getIntValueFromInstrumentMode(sharedPrefsManager.getInstrumentModeInSharedPrefs())
        ).map {
            songRemoteEntityDataMapper.transformListRemoteEntitiesToListEntities(it)
        }
    }

    fun retrieveSongFromId(idSong: String): Single<SongEntity> {
        return apiManager.retrieveSongFromId(idSong).map {
            songRemoteEntityDataMapper.transformRemoteEntityToEntity(it)
        }
    }

    fun createSong(songEntity: SongEntity): Completable {
        return apiManager.createSong(songRemoteEntityDataMapper.transformEntityToRemoteEntity(songEntity))
    }

    fun removeSong(idSong: String): Completable {
        return apiManager.removeSong(idSong)
    }

    fun updateSong(songEntity: SongEntity): Completable {
        return apiManager.updateSong(songRemoteEntityDataMapper.transformEntityToRemoteEntity(songEntity))
    }

    fun sendScoreFeedback(scoreFeedbackEntity: ScoreFeedbackEntity, idSong: String): Completable {
        return apiManager.sendScoreFeedback(
                scoreFeedbackRemoteEntityDataMapper.transformEntityToRemoteEntity(
                        scoreFeedbackEntity
                ), idSong
        )
    }

    fun retrieveSongScoreHistoric(idSong: String): Single<List<ScoreEntity>> {
        return apiManager.retrieveSongScoreHistoric(idSong).map {
            scoreRemoteEntityDataMapper.transformListRemoteEntitiesToListEntities(it)
        }
    }
}
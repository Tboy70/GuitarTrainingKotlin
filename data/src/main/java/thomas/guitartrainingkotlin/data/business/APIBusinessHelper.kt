package thomas.guitartrainingkotlin.data.business

import io.reactivex.Completable
import io.reactivex.Single
import thomas.guitartrainingkotlin.data.entity.*
import thomas.guitartrainingkotlin.data.manager.api.ApiManager
import thomas.guitartrainingkotlin.data.manager.db.DBManager
import thomas.guitartrainingkotlin.data.manager.sharedprefs.SharedPrefsManager
import thomas.guitartrainingkotlin.data.mapper.db.SongDBEntityDataMapper
import thomas.guitartrainingkotlin.data.mapper.remote.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class APIBusinessHelper @Inject constructor(
    private val dbManager: DBManager,
    private val apiManager: ApiManager,
    private val sharedPrefsManager: SharedPrefsManager,
    private val userRemoteEntityDataMapper: UserRemoteEntityDataMapper,
    private val songRemoteEntityDataMapper: SongRemoteEntityDataMapper,
    private val scoreRemoteEntityDataMapper: ScoreRemoteEntityDataMapper,
    private val songDBEntityDataMapper: SongDBEntityDataMapper,
    private val programRemoteEntityDataMapper: ProgramRemoteEntityDataMapper,
    private val exerciseRemoteEntityDataMapper: ExerciseRemoteEntityDataMapper,
    private val scoreFeedbackRemoteEntityDataMapper: ScoreFeedbackRemoteEntityDataMapper
) {

    fun connectUser(userEntity: UserEntity): Single<UserEntity> {
        return apiManager.connectUser(userRemoteEntityDataMapper.transformFromEntity(userEntity))
            .map {
                userRemoteEntityDataMapper.transformToEntity(it)
            }
    }

    fun retrieveUserById(userId: String): Single<UserEntity> {
        return apiManager.retrieveUserById(userId).map {
            userRemoteEntityDataMapper.transformToEntity(it)
        }
    }

    fun createNewUser(userEntity: UserEntity): Completable {
        return apiManager.createNewUser(userRemoteEntityDataMapper.transformFromEntity(userEntity))
    }

    fun suppressAccount(userId: String): Completable {
        return apiManager.suppressAccount(userId)
    }

    fun retrieveProgramListByUserId(userId: String): Single<List<ProgramEntity>> {
        return apiManager.retrieveProgramsListByUserId(
            userId, sharedPrefsManager.getInstrumentModeInSharedPrefs()
        ).map {
            programRemoteEntityDataMapper.transformToEntity(it)
        }
    }

    fun retrieveProgramFromId(idProgram: String): Single<ProgramEntity> {
        return apiManager.retrieveProgramFromId(idProgram).map {
            programRemoteEntityDataMapper.transformToEntity(it)
        }
    }

    fun createProgram(programEntity: ProgramEntity): Single<String> {
        return apiManager.createProgram(programRemoteEntityDataMapper.transformFromEntity(programEntity)).map {
            it
        }
    }

    fun createExercise(exerciseEntityList: List<ExerciseEntity>): Completable {
        return apiManager.createExercise(
            exerciseRemoteEntityDataMapper.transformFromEntity(
                exerciseEntityList
            )
        )
    }

    fun updateProgram(programEntity: ProgramEntity, exerciseEntityList: List<ExerciseEntity>): Completable {
        return apiManager.removeExercises(
            exerciseRemoteEntityDataMapper.transformFromEntity(
                exerciseEntityList
            )
        ).concatWith(
            apiManager.updateProgram(
                programRemoteEntityDataMapper.transformFromEntity(
                    programEntity
                )
            )
        ).concatWith(
            apiManager.updateExercise(
                exerciseRemoteEntityDataMapper.transformFromEntity(
                    programEntity.exerciseEntityList
                )
            )
        )
    }

    fun removeProgram(idProgram: String): Completable {
        return apiManager.removeProgram(idProgram)
    }

    fun retrieveSongListByUserId(userId: String): Single<List<SongEntity>> {
        val songListInDB = dbManager.retrieveSongList()
        return if (songListInDB.isNotEmpty()) {
            Single.just(songDBEntityDataMapper.transformFromDB(songListInDB))
        } else {
            apiManager.retrieveSongListByUserId(
                userId, sharedPrefsManager.getInstrumentModeInSharedPrefs()
            ).map {
                songRemoteEntityDataMapper.transformToEntity(it)
            }.doOnSuccess {
                dbManager.deleteSong()
                dbManager.insertSongList(songDBEntityDataMapper.transformToDB(it))
            }
        }
    }

    fun retrieveSongFromId(idSong: String): Single<SongEntity> {
        return apiManager.retrieveSongFromId(idSong).map {
            songRemoteEntityDataMapper.transformToEntity(it)
        }
    }

    fun createSong(songEntity: SongEntity): Completable {
        return apiManager.createSong(songRemoteEntityDataMapper.transformFromEntity(songEntity))
    }

    fun removeSong(idSong: String): Completable {
        return apiManager.removeSong(idSong).doOnComplete {
            dbManager.deleteSongById(idSong)
        }
    }

    fun updateSong(songEntity: SongEntity): Completable {
        return apiManager.updateSong(songRemoteEntityDataMapper.transformFromEntity(songEntity))
            .doOnComplete {
                dbManager.updateSong(songDBEntityDataMapper.transformToDB(songEntity))
            }
    }

    fun sendScoreFeedback(scoreFeedbackEntity: ScoreFeedbackEntity, idSong: String): Completable {
        return apiManager.sendScoreFeedback(
            scoreFeedbackRemoteEntityDataMapper.transformFromEntity(
                scoreFeedbackEntity
            ), idSong
        )
    }

    fun retrieveSongScoreHistory(idSong: String): Single<List<ScoreEntity>> {
        return apiManager.retrieveSongScoreHistory(idSong).map {
            scoreRemoteEntityDataMapper.transformToEntity(it)
        }
    }
}
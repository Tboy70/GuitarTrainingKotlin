package thomas.example.com.data.business

import io.reactivex.Completable
import io.reactivex.Single
import thomas.example.com.data.entity.*
import thomas.example.com.data.manager.ApiManager
import thomas.example.com.data.manager.SharedPrefsManager
import thomas.example.com.data.mapper.remote.*
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
            userId,
            InstrumentModeUtils.getIntValueFromInstrumentMode(sharedPrefsManager.getInstrumentModeInSharedPrefs())
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
        return apiManager.retrieveSongListByUserId(
            userId,
            InstrumentModeUtils.getIntValueFromInstrumentMode(sharedPrefsManager.getInstrumentModeInSharedPrefs())
        ).map {
            songRemoteEntityDataMapper.transformToEntity(it)
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
        return apiManager.removeSong(idSong)
    }

    fun updateSong(songEntity: SongEntity): Completable {
        return apiManager.updateSong(songRemoteEntityDataMapper.transformFromEntity(songEntity))
    }

    fun sendScoreFeedback(scoreFeedbackEntity: ScoreFeedbackEntity, idSong: String): Completable {
        return apiManager.sendScoreFeedback(
            scoreFeedbackRemoteEntityDataMapper.transformFromEntity(
                scoreFeedbackEntity
            ), idSong
        )
    }

    fun retrieveSongScoreHistoric(idSong: String): Single<List<ScoreEntity>> {
        return apiManager.retrieveSongScoreHistoric(idSong).map {
            scoreRemoteEntityDataMapper.transformToEntity(it)
        }
    }
}
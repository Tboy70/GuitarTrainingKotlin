package thomas.example.com.data.repository.client

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import thomas.example.com.data.entity.*
import thomas.example.com.data.mapper.remote.*
import thomas.example.com.data.module.ApiModule
import thomas.example.com.data.module.ModuleSharedPrefsImpl
import thomas.example.com.data.utils.InstrumentModeUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class APIClient @Inject constructor(
        private val apiModule: ApiModule,
        private val moduleSharedPrefsImpl: ModuleSharedPrefsImpl,
        private val programRemoteEntityDataMapper: ProgramRemoteEntityDataMapper,
        private val exerciseRemoteEntityDataMapper: ExerciseRemoteEntityDataMapper,
        private val userRemoteEntityDataMapper: UserRemoteEntityDataMapper,
        private val songRemoteEntityDataMapper: SongRemoteEntityDataMapper,
        private val scoreFeedbackRemoteEntityDataMapper: ScoreFeedbackRemoteEntityDataMapper,
        private val scoreRemoteEntityDataMapper: ScoreRemoteEntityDataMapper
) {

    fun connectUser(userEntity: UserEntity): Single<UserEntity>? {
        return apiModule.connectUser(userRemoteEntityDataMapper.transformEntityToRemoteEntity(userEntity))
            .map {
                userRemoteEntityDataMapper.transformRemoteEntityToEntity(it)
            }
    }

    fun retrieveUserById(idUser: String): Single<UserEntity> {
        return apiModule.retrieveUserById(idUser).map {
            userRemoteEntityDataMapper.transformRemoteEntityToEntity(it)
        }
    }

    fun createNewUser(userEntity: UserEntity): Completable {
        return apiModule.createNewUser(userRemoteEntityDataMapper.transformEntityToRemoteEntity(userEntity))
    }

    fun suppressAccount(idUser: String): Completable {
        return apiModule.suppressAccount(idUser)
    }

    fun retrieveProgramsListByUserId(idUser: String): Single<List<ProgramEntity>> {
        return apiModule.retrieveProgramsListByUserId(
                idUser,
                InstrumentModeUtils.getIntValueFromInstrumentMode(moduleSharedPrefsImpl.getInstrumentModeInSharedPrefs())
        ).map {
            programRemoteEntityDataMapper.transformListRemoteEntitiesToListEntities(it)
        }
    }

    fun retrieveProgramFromId(idProgram: String): Single<ProgramEntity> {
        return apiModule.retrieveProgramFromId(idProgram).map {
            programRemoteEntityDataMapper.transformRemoteEntityToEntity(it)
        }
    }

    fun createProgram(programEntity: ProgramEntity): Single<String> {
        return apiModule.createProgram(programRemoteEntityDataMapper.transformEntityToRemoteEntity(programEntity)).map {
            it
        }
    }

    fun createExercise(listExercisesEntities: List<ExerciseEntity>): Completable {
        return apiModule.createExercise(
                exerciseRemoteEntityDataMapper.transformListEntitiesToListRemoteEntities(
                        listExercisesEntities
                )
        )
    }

    fun updateProgram(programEntity: ProgramEntity, exerciseEntityList: List<ExerciseEntity>): Completable {
        return apiModule.removeExercises(
                exerciseRemoteEntityDataMapper.transformListEntitiesToListRemoteEntities(
                        exerciseEntityList
                )
        ).concatWith(
                apiModule.updateProgram(
                        programRemoteEntityDataMapper.transformEntityToRemoteEntity(
                                programEntity
                        )
                )
        ).concatWith(
                apiModule.updateExercise(
                        exerciseRemoteEntityDataMapper.transformListEntitiesToListRemoteEntities(
                                programEntity.exerciseEntities
                        )
                )
        )
    }

    fun removeProgram(idProgram: String): Completable {
        return apiModule.removeProgram(idProgram)
    }

    fun retrieveSongsListByUserId(idUser: String): Single<List<SongEntity>> {
        return apiModule.retrieveSongsListByUserId(
                idUser,
                InstrumentModeUtils.getIntValueFromInstrumentMode(moduleSharedPrefsImpl.getInstrumentModeInSharedPrefs())
        ).map {
            songRemoteEntityDataMapper.transformListRemoteEntitiesToListEntities(it)
        }
    }

    fun retrieveSongFromId(idSong: String): Single<SongEntity> {
        return apiModule.retrieveSongFromId(idSong).map {
            songRemoteEntityDataMapper.transformRemoteEntityToEntity(it)
        }
    }

    fun createSong(songEntity: SongEntity): Completable {
        return apiModule.createSong(songRemoteEntityDataMapper.transformEntityToRemoteEntity(songEntity))
    }

    fun removeSong(idSong: String): Completable {
        return apiModule.removeSong(idSong)
    }

    fun updateSong(songEntity: SongEntity): Completable {
        return apiModule.updateSong(songRemoteEntityDataMapper.transformEntityToRemoteEntity(songEntity))
    }

    fun sendScoreFeedback(scoreFeedbackEntity: ScoreFeedbackEntity, idSong: String): Completable {
        return apiModule.sendScoreFeedback(
                scoreFeedbackRemoteEntityDataMapper.transformEntityToRemoteEntity(
                        scoreFeedbackEntity
                ), idSong
        )
    }

    fun retrieveSongScoreHistoric(idSong: String): Single<List<ScoreEntity>> {
        return apiModule.retrieveSongScoreHistoric(idSong).map {
            scoreRemoteEntityDataMapper.transformListRemoteEntitiesToListEntities(it)
        }
    }
}
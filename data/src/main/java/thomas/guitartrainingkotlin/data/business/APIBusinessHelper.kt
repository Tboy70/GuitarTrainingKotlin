package thomas.guitartrainingkotlin.data.business

import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import thomas.guitartrainingkotlin.data.entity.*
import thomas.guitartrainingkotlin.data.entity.db.ExerciseDBEntity
import thomas.guitartrainingkotlin.data.exception.ProgramNotFoundException
import thomas.guitartrainingkotlin.data.exception.SongNotFoundException
import thomas.guitartrainingkotlin.data.manager.api.ApiManager
import thomas.guitartrainingkotlin.data.manager.db.DBManager
import thomas.guitartrainingkotlin.data.manager.sharedprefs.SharedPrefsManager
import thomas.guitartrainingkotlin.data.mapper.db.ExerciseDBEntityDataMapper
import thomas.guitartrainingkotlin.data.mapper.db.ProgramDBEntityDataMapper
import thomas.guitartrainingkotlin.data.mapper.db.ScoreDBEntityDataMapper
import thomas.guitartrainingkotlin.data.mapper.db.SongDBEntityDataMapper
import thomas.guitartrainingkotlin.data.mapper.remote.*
import javax.inject.Inject
import javax.inject.Singleton

@FlowPreview
@ExperimentalCoroutinesApi
@Singleton
class APIBusinessHelper @Inject constructor(
    private val dbManager: DBManager,
    private val apiManager: ApiManager,
    private val sharedPrefsManager: SharedPrefsManager,
    private val songDBEntityDataMapper: SongDBEntityDataMapper,
    private val scoreDBEntityDataMapper: ScoreDBEntityDataMapper,
    private val programDBEntityDataMapper: ProgramDBEntityDataMapper,
    private val exerciseDBEntityDataMapper: ExerciseDBEntityDataMapper,
    private val userRemoteEntityDataMapper: UserRemoteEntityDataMapper,
    private val songRemoteEntityDataMapper: SongRemoteEntityDataMapper,
    private val scoreRemoteEntityDataMapper: ScoreRemoteEntityDataMapper,
    private val programRemoteEntityDataMapper: ProgramRemoteEntityDataMapper,
    private val exerciseRemoteEntityDataMapper: ExerciseRemoteEntityDataMapper,
    private val scoreFeedbackRemoteEntityDataMapper: ScoreFeedbackRemoteEntityDataMapper
) {

    fun connectUser(userEntity: UserEntity): Flow<UserEntity> {
        return apiManager.connectUser(userRemoteEntityDataMapper.transformFromEntity(userEntity))
            .map {
                userRemoteEntityDataMapper.transformToEntity(it)
            }
    }

    fun retrieveUserById(userId: String): Flow<UserEntity> {
        return apiManager.retrieveUserById(userId).map {
            userRemoteEntityDataMapper.transformToEntity(it)
        }
    }

    fun retrievePassword(emailAddress: String): Flow<Unit> {
        return apiManager.retrievePassword(emailAddress)
    }

    fun createNewUser(userEntity: UserEntity): Flow<Unit> {
        return apiManager.createNewUser(userRemoteEntityDataMapper.transformFromEntity(userEntity))
    }

    fun suppressAccount(userId: String): Flow<Unit> {
        return apiManager.suppressAccount(userId)
    }

    fun retrieveProgramListByUserId(userId: String): Flow<List<ProgramEntity>> {
        return sharedPrefsManager.getInstrumentModeInSharedPrefs()
            .flatMapConcat {
                apiManager.retrieveProgramsListByUserId(
                    userId, it
                ).map {
                    programRemoteEntityDataMapper.transformToEntity(it)
                }.onEach {

                    dbManager.deleteProgram()
                    dbManager.deleteExercise()

                    val programDBEntityList = programDBEntityDataMapper.transformToDB(it)
                    dbManager.insertProgramList(programDBEntityList)

                    val exerciseList = mutableListOf<ExerciseDBEntity>()
                    programDBEntityList.forEach { programDBEntity ->
                        programDBEntity.exerciseList?.forEach { exerciseDBEntity ->
                            exerciseList.add(exerciseDBEntity)
                        }
                    }
                    if (exerciseList.isNotEmpty()) {
                        dbManager.insertExerciseList(exerciseList)
                    }
                }.catch {
                    /** Replace on error resume next ? To check ! */
                    programDBEntityDataMapper.transformFromDB(dbManager.retrieveProgramList())
                }
            }
//        apiManager.retrieveProgramsListByUserId(
//            userId, sharedPrefsManager.getInstrumentModeInSharedPrefs()
//        ).map {
//            programRemoteEntityDataMapper.transformToEntity(it)
//        }.onEach {
//
//            dbManager.deleteProgram()
//            dbManager.deleteExercise()
//
//            val programDBEntityList = programDBEntityDataMapper.transformToDB(it)
//            dbManager.insertProgramList(programDBEntityList)
//
//            val exerciseList = mutableListOf<ExerciseDBEntity>()
//            programDBEntityList.forEach { programDBEntity ->
//                programDBEntity.exerciseList?.forEach { exerciseDBEntity ->
//                    exerciseList.add(exerciseDBEntity)
//                }
//            }
//            if (exerciseList.isNotEmpty()) {
//                dbManager.insertExerciseList(exerciseList)
//            }
//        }.catch {
//            /** Replace on error resume next ? To check ! */
//            programDBEntityDataMapper.transformFromDB(dbManager.retrieveProgramList())
//        }
    }

    fun retrieveProgramFromId(idProgram: String): Flow<ProgramEntity> {
        return apiManager.retrieveProgramFromId(idProgram).map {
            programRemoteEntityDataMapper.transformToEntity(it)
        }.catch {
            /** Replace on error resume next ? To check ! */
            dbManager.retrieveProgramById(idProgram)?.let {
                Single.just(programDBEntityDataMapper.transformFromDB(it))
            } ?: Single.error(ProgramNotFoundException())
        }
    }

    fun createProgram(programEntity: ProgramEntity): Flow<String?> {
        return apiManager.createProgram(
            programRemoteEntityDataMapper.transformFromEntity(programEntity)
        ).map { idProgram ->
            idProgram
        }.onEach {
            it?.let {
                dbManager.insertProgram(programDBEntityDataMapper.transformToDB(programEntity.apply {
                    this.idProgram = it
                }))
            }
        }
    }

    fun createExercise(exerciseEntityList: List<ExerciseEntity>): Flow<Unit> {
        return apiManager.createExercise(
            exerciseRemoteEntityDataMapper.transformFromEntity(
                exerciseEntityList
            )
        ).onCompletion {
            dbManager.insertExerciseList(exerciseDBEntityDataMapper.transformToDB(exerciseEntityList))
        }
    }

    fun updateProgram(
        programEntity: ProgramEntity,
        exerciseEntityList: List<ExerciseEntity>
    ): Flow<Unit> {
        return apiManager.removeExercises(
            exerciseRemoteEntityDataMapper.transformFromEntity(
                exerciseEntityList
            )
        ).flatMapConcat {
            apiManager.updateProgram(
                programRemoteEntityDataMapper.transformFromEntity(
                    programEntity
                )
            ).onCompletion {
                dbManager.updateProgram(programDBEntityDataMapper.transformToDB(programEntity))
            }
        }.flatMapConcat {
            apiManager.updateExercise(
                exerciseRemoteEntityDataMapper.transformFromEntity(
                    programEntity.exerciseEntityList
                )
            )
        }
    }

    fun removeProgram(idProgram: String): Flow<Unit> {
        return apiManager.removeProgram(idProgram).onCompletion {
            dbManager.deleteProgramById(idProgram)
        }
    }

    fun retrieveSongListByUserId(userId: String): Flow<List<SongEntity>> {
        return sharedPrefsManager.getInstrumentModeInSharedPrefs().flatMapConcat {
            apiManager.retrieveSongListByUserId(
                userId, it
            ).map {
                songRemoteEntityDataMapper.transformToEntity(it)
            }.onEach {
                /** Replace doOnSuccess ? */
                dbManager.deleteSong()
                dbManager.insertSongList(songDBEntityDataMapper.transformToDB(it))
            }.catch {
                /** replace onErrorResumeNext ? */
                songDBEntityDataMapper.transformFromDB(dbManager.retrieveSongList())
            }
        }
//        return apiManager.retrieveSongListByUserId(
//            userId, sharedPrefsManager.getInstrumentModeInSharedPrefs()
//        ).map {
//            songRemoteEntityDataMapper.transformToEntity(it)
//        }.onEach {
//            /** Replace doOnSuccess ? */
//            dbManager.deleteSong()
//            dbManager.insertSongList(songDBEntityDataMapper.transformToDB(it))
//        }.catch {
//            /** replace onErrorResumeNext ? */
//            songDBEntityDataMapper.transformFromDB(dbManager.retrieveSongList())
//        }
    }

    fun retrieveSongFromId(idSong: String): Flow<SongEntity> {
        return apiManager.retrieveSongFromId(idSong).map {
            songRemoteEntityDataMapper.transformToEntity(it)
        }.catch {
            /** replace onErrorResumeNext ? */
            dbManager.retrieveSongById(idSong)?.let {
                songDBEntityDataMapper.transformFromDB(it)
            } ?: throw SongNotFoundException()
        }
    }

    fun createSong(songEntity: SongEntity): Flow<Unit> {
        return apiManager.createSong(songRemoteEntityDataMapper.transformFromEntity(songEntity))
            .onCompletion {
                dbManager.insertSong(songDBEntityDataMapper.transformToDB(songEntity))
            }
    }

    fun removeSong(idSong: String): Flow<Unit> {
        return apiManager.removeSong(idSong).onCompletion {
            dbManager.deleteSongById(idSong)
        }
    }

    fun updateSong(songEntity: SongEntity): Flow<Unit> {
        return apiManager.updateSong(songRemoteEntityDataMapper.transformFromEntity(songEntity))
            .onCompletion {
                dbManager.updateSong(songDBEntityDataMapper.transformToDB(songEntity))
            }
    }

    fun sendScoreFeedback(scoreFeedbackEntity: ScoreFeedbackEntity, idSong: String): Flow<Unit> {
        return apiManager.sendScoreFeedback(
            scoreFeedbackRemoteEntityDataMapper.transformFromEntity(
                scoreFeedbackEntity
            ), idSong
        )
    }

    fun retrieveSongScoreHistory(idSong: String): Flow<List<ScoreEntity>> {
        return apiManager.retrieveSongScoreHistory(idSong).map {
            scoreRemoteEntityDataMapper.transformToEntity(it)
        }.onEach {
            dbManager.updateSongScore(idSong, scoreDBEntityDataMapper.transformToDB(it))
        }.catch {
            scoreDBEntityDataMapper.transformFromDB(dbManager.retrieveSongScore(idSong))
        }
    }
}
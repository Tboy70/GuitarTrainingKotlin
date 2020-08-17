package thomas.guitartrainingkotlin.data.business

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import thomas.guitartrainingkotlin.data.entity.*
import thomas.guitartrainingkotlin.data.entity.db.ExerciseDBEntity
import thomas.guitartrainingkotlin.data.exception.ProgramNotFoundException
import thomas.guitartrainingkotlin.data.exception.SongNotFoundException
import thomas.guitartrainingkotlin.data.exception.UserNotFoundException
import thomas.guitartrainingkotlin.data.manager.api.ApiManager
import thomas.guitartrainingkotlin.data.manager.db.DBManager
import thomas.guitartrainingkotlin.data.manager.sharedprefs.SharedPrefsManager
import thomas.guitartrainingkotlin.data.mapper.db.*
import thomas.guitartrainingkotlin.data.mapper.remote.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@FlowPreview
@ExperimentalCoroutinesApi
class APIBusinessHelper @Inject constructor(
    private val dbManager: DBManager,
    private val apiManager: ApiManager,
    private val sharedPrefsManager: SharedPrefsManager,
    private val userDBEntityDataMapper: UserDBEntityDataMapper,
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

    fun retrieveUserId(): Flow<String?> {
        return flowOf(dbManager.retrieveUser()?.userId)
    }

    fun connectUser(userEntity: UserEntity): Flow<UserEntity> {
        return apiManager.connectUser(userRemoteEntityDataMapper.transformFromEntity(userEntity))
            .map {
                userRemoteEntityDataMapper.transformToEntity(it)
            }.onEach {
                dbManager.clearUser()
                dbManager.insertUser(userDBEntityDataMapper.transformToDB(it))
            }
    }

    fun retrieveUserById(userId: String): Flow<UserEntity> {
        return apiManager.retrieveUserById(userId).map {
            userRemoteEntityDataMapper.transformToEntity(it)
        }.catch {
            val userFromDB = dbManager.retrieveUser()?.let {
                userDBEntityDataMapper.transformFromDB(it)
            } ?: throw UserNotFoundException()

            emitAll(flowOf(userFromDB))
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

    /**
     * onErrorResumeNext is replaced by catch { emitAll() }
     */
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
                    emitAll(flowOf(programDBEntityDataMapper.transformFromDB(dbManager.retrieveProgramList())))
                }
            }
    }

    fun retrieveProgramFromId(idProgram: String): Flow<ProgramEntity> {
        return apiManager.retrieveProgramFromId(idProgram).map {
            programRemoteEntityDataMapper.transformToEntity(it)
        }.catch {
            val programFromDB = dbManager.retrieveProgramById(idProgram)?.let {
                programDBEntityDataMapper.transformFromDB(it)
            } ?: throw ProgramNotFoundException()

            emitAll(flowOf(programFromDB))

        }
    }

    fun createProgram(programEntity: ProgramEntity): Flow<String> {
        return apiManager.createProgram(
            programRemoteEntityDataMapper.transformFromEntity(
                programEntity
            )
        ).onEach {
            dbManager.insertProgram(programDBEntityDataMapper.transformToDB(programEntity.apply {
                this.idProgram = it
            }))
        }
    }

    fun createExercise(exerciseEntityList: List<ExerciseEntity>): Flow<Unit> {
        return apiManager.createExercise(
            exerciseRemoteEntityDataMapper.transformFromEntity(
                exerciseEntityList
            )
        ).map {
            dbManager.insertExerciseList(exerciseDBEntityDataMapper.transformToDB(exerciseEntityList))
        }
    }

    /**
     * TODO : Check if exercises are removed or update in database
     */
    fun updateProgram(
        programEntity: ProgramEntity,
        exercisesToBeRemoved: List<ExerciseEntity>
    ): Flow<Unit> {
        return apiManager.removeExercises(
            exerciseRemoteEntityDataMapper.transformFromEntity(
                exercisesToBeRemoved
            )
        ).flatMapMerge {
            val updateProgramFlow = apiManager.updateProgram(
                programRemoteEntityDataMapper.transformFromEntity(
                    programEntity
                )
            ).map {
                dbManager.updateProgram(programDBEntityDataMapper.transformToDB(programEntity))
            }

            val updateExercisesFlow = apiManager.updateExercise(
                exerciseRemoteEntityDataMapper.transformFromEntity(
                    programEntity.exerciseEntityList
                )
            )

            merge(updateProgramFlow, updateExercisesFlow)
        }
    }

//            .flatMapConcat {
//            apiManager.updateProgram(
//                programRemoteEntityDataMapper.transformFromEntity(
//                    programEntity
//                )
//            ).onEach {
//                apiManager.updateExercise(
//                    exerciseRemoteEntityDataMapper.transformFromEntity(
//                        programEntity.exerciseEntityList
//                    )
//                )
//            }

//                .map {
//                dbManager.updateProgram(programDBEntityDataMapper.transformToDB(programEntity))
//            }.flatMapConcat {
//                apiManager.updateExercise(
//                    exerciseRemoteEntityDataMapper.transformFromEntity(
//                        programEntity.exerciseEntityList
//                    )
//                )
//            }
//    }
//            .flatMapMerge {
//            apiManager.updateExercise(
//                exerciseRemoteEntityDataMapper.transformFromEntity(
//                    programEntity.exerciseEntityList
//                )
//            )
//        }

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
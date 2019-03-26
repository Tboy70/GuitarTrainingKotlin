package thomas.guitartrainingkotlin.data.business

import io.reactivex.Completable
import io.reactivex.Single
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
        // TODO : What should I do --> the commented way or this one ?
//        val programListInDB = dbManager.retrieveProgramList()
//        return if (programListInDB.isNotEmpty()) {
//            Single.just(programDBEntityDataMapper.transformFromDB(programListInDB))
//        } else {
//            apiManager.retrieveProgramsListByUserId(
//                userId, sharedPrefsManager.getInstrumentModeInSharedPrefs()
//            ).map {
//                programRemoteEntityDataMapper.transformToEntity(it)
//            }.doOnSuccess {
//
//                dbManager.deleteProgram()
//                dbManager.deleteExercise()
//
//                val programDBEntityList = programDBEntityDataMapper.transformToDB(it)
//                dbManager.insertProgramList(programDBEntityList)
//
//                val exerciseList = mutableListOf<ExerciseDBEntity>()
//                programDBEntityList.forEach { programDBEntity ->
//                    programDBEntity.exerciseList?.forEach { exerciseDBEntity ->
//                        exerciseList.add(exerciseDBEntity)
//                    }
//                }
//                if (exerciseList.isNotEmpty()) {
//                    dbManager.insertExerciseList(exerciseList)
//                }
//            }
//        }

        return apiManager.retrieveProgramsListByUserId(
            userId, sharedPrefsManager.getInstrumentModeInSharedPrefs()
        ).map {
            programRemoteEntityDataMapper.transformToEntity(it)
        }.doOnSuccess {

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
        }.onErrorResumeNext {
            Single.just(programDBEntityDataMapper.transformFromDB(dbManager.retrieveProgramList()))
        }
    }

    fun retrieveProgramFromId(idProgram: String): Single<ProgramEntity> {
        return apiManager.retrieveProgramFromId(idProgram).map {
            programRemoteEntityDataMapper.transformToEntity(it)
        }.onErrorResumeNext {
            dbManager.retrieveProgramById(idProgram)?.let {
                Single.just(programDBEntityDataMapper.transformFromDB(it))
            } ?: Single.error(ProgramNotFoundException())
        }
    }

    fun createProgram(programEntity: ProgramEntity): Single<String> {
        return apiManager.createProgram(
            programRemoteEntityDataMapper.transformFromEntity(programEntity)
        ).map { idProgram ->
            idProgram
        }.doOnSuccess {
            dbManager.insertProgram(programDBEntityDataMapper.transformToDB(programEntity.apply {
                this.idProgram = it
            }))
        }
    }

    fun createExercise(exerciseEntityList: List<ExerciseEntity>): Completable {
        return apiManager.createExercise(
            exerciseRemoteEntityDataMapper.transformFromEntity(
                exerciseEntityList
            )
        ).doOnComplete {
            dbManager.insertExerciseList(exerciseDBEntityDataMapper.transformToDB(exerciseEntityList))
        }
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
            ).doOnComplete {
                dbManager.updateProgram(programDBEntityDataMapper.transformToDB(programEntity))
            }
        ).concatWith(
            apiManager.updateExercise(
                exerciseRemoteEntityDataMapper.transformFromEntity(
                    programEntity.exerciseEntityList
                )
            )
        )
    }

    fun removeProgram(idProgram: String): Completable {
        return apiManager.removeProgram(idProgram).doOnComplete {
            dbManager.deleteProgramById(idProgram)
        }
    }

    fun retrieveSongListByUserId(userId: String): Single<List<SongEntity>> {
        // TODO : What should I do --> the commented way or this one ?
//        val songListInDB = dbManager.retrieveSongList()
//        return if (songListInDB.isNotEmpty()) {
//            Single.just(songDBEntityDataMapper.transformFromDB(songListInDB))
//        } else {
//            apiManager.retrieveSongListByUserId(
//                userId, sharedPrefsManager.getInstrumentModeInSharedPrefs()
//            ).map {
//                songRemoteEntityDataMapper.transformToEntity(it)
//            }.doOnSuccess {
//                dbManager.deleteSong()
//                dbManager.insertSongList(songDBEntityDataMapper.transformToDB(it))
//            }
//        }

        return apiManager.retrieveSongListByUserId(
            userId, sharedPrefsManager.getInstrumentModeInSharedPrefs()
        ).map {
            songRemoteEntityDataMapper.transformToEntity(it)
        }.doOnSuccess {
            dbManager.deleteSong()
            dbManager.insertSongList(songDBEntityDataMapper.transformToDB(it))
        }.onErrorResumeNext {
            Single.just(songDBEntityDataMapper.transformFromDB(dbManager.retrieveSongList()))
        }
    }

    fun retrieveSongFromId(idSong: String): Single<SongEntity> {
        return apiManager.retrieveSongFromId(idSong).map {
            songRemoteEntityDataMapper.transformToEntity(it)
        }.onErrorResumeNext {
            dbManager.retrieveSongById(idSong)?.let {
                Single.just(songDBEntityDataMapper.transformFromDB(it))
            } ?: Single.error(SongNotFoundException())
        }
    }

    fun createSong(songEntity: SongEntity): Completable {
        return apiManager.createSong(songRemoteEntityDataMapper.transformFromEntity(songEntity))
            .doOnComplete {
                dbManager.insertSong(songDBEntityDataMapper.transformToDB(songEntity))
            }
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
        }.doOnSuccess {
            dbManager.updateSongScore(idSong, scoreDBEntityDataMapper.transformToDB(it))
        }.onErrorResumeNext {
            Single.just(scoreDBEntityDataMapper.transformFromDB(dbManager.retrieveSongScore(idSong)))
        }
    }
}
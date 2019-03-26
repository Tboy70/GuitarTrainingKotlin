package thomas.guitartrainingkotlin.data.manager.db

import android.content.Context
import com.raizlabs.android.dbflow.annotation.Database
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.kotlinextensions.*
import thomas.guitartrainingkotlin.data.entity.db.*
import thomas.guitartrainingkotlin.data.values.DBValues
import javax.inject.Inject
import javax.inject.Singleton

@Database(version = DBValues.DB_VERSION)
@Singleton
class DBManagerImpl @Inject constructor(context: Context) : DBManager {

    init {
        FlowManager.init(FlowConfig.Builder(context).build())
    }

    override fun retrieveProgramList(): List<ProgramDBEntity> {
        return (select from ProgramDBEntity::class).list
    }

    override fun retrieveProgramById(idProgram: String): ProgramDBEntity? {
        return (select from ProgramDBEntity::class where (ProgramDBEntity_Table.idProgram eq idProgram)).result
    }

    override fun insertProgramList(programDBEntityList: List<ProgramDBEntity>) {
        programDBEntityList.processInTransaction { programDBEntity, databaseWrapper ->
            programDBEntity.save(databaseWrapper)
        }
    }

    override fun insertProgram(programDBEntity: ProgramDBEntity) {
        programDBEntity.save()
    }

    override fun updateProgram(programDBEntity: ProgramDBEntity) {
        programDBEntity.save()
    }

    override fun deleteProgramById(idProgram: String) {
        (delete(ProgramDBEntity::class) where (ProgramDBEntity_Table.idProgram eq idProgram)).execute()
    }

    override fun deleteProgram() {
        (delete(ProgramDBEntity::class)).execute()
    }

    override fun insertExerciseList(exerciseDBEntityList: List<ExerciseDBEntity>) {
        exerciseDBEntityList.processInTransaction { exerciseDBEntity, databaseWrapper ->
            exerciseDBEntity.save(databaseWrapper)
        }
    }

    override fun deleteExercise() {
        (delete(ExerciseDBEntity::class)).execute()
    }

    override fun retrieveSongList(): List<SongDBEntity> {
        return (select from SongDBEntity::class).list
    }

    override fun retrieveSongById(idSong: String): SongDBEntity? {
        return (select from SongDBEntity::class where (SongDBEntity_Table.idSong eq idSong)).result
    }

    override fun retrieveSongScore(idSong: String): List<ScoreDBEntity> {
        return (select from ScoreDBEntity::class where (ScoreDBEntity_Table.idSong eq idSong)).list
    }

    override fun updateSongScore(idSong: String, scoreDBEntityList: List<ScoreDBEntity>) {
        scoreDBEntityList.processInTransaction { scoreDBEntity, databaseWrapper ->
            scoreDBEntity.save(databaseWrapper)
        }
    }

    override fun insertSongList(songDBEntityList: List<SongDBEntity>) {
        songDBEntityList.processInTransaction { songDBEntity, databaseWrapper ->
            songDBEntity.save(databaseWrapper)
        }
    }

    override fun insertSong(songDBEntity: SongDBEntity) {
        songDBEntity.save()
    }

    override fun updateSong(songDBEntity: SongDBEntity) {
        songDBEntity.save()
    }

    override fun deleteSongById(idSong: String) {
        (delete(SongDBEntity::class) where (SongDBEntity_Table.idSong eq idSong)).execute()
    }

    override fun deleteSong() {
        (delete(SongDBEntity::class)).execute()
    }
}
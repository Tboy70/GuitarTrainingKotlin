package thomas.guitartrainingkotlin.data.manager.db

import android.content.Context
import com.raizlabs.android.dbflow.annotation.Database
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.kotlinextensions.*
import thomas.guitartrainingkotlin.data.entity.db.SongDBEntity
import thomas.guitartrainingkotlin.data.entity.db.SongDBEntity_Table
import thomas.guitartrainingkotlin.data.values.DBValues
import javax.inject.Inject
import javax.inject.Singleton

@Database(version = DBValues.DB_VERSION)
@Singleton
class DBManagerImpl @Inject constructor(context: Context) : DBManager {

    init {
        FlowManager.init(FlowConfig.Builder(context).build())
    }

    override fun insertSongList(songDBEntityList: List<SongDBEntity>) {
        songDBEntityList.processInTransaction { songDBEntity, databaseWrapper ->
            songDBEntity.save(databaseWrapper)
        }
    }

    override fun retrieveSongList(): List<SongDBEntity> {
        return (select from SongDBEntity::class).list
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
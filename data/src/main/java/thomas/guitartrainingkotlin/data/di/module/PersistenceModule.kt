package thomas.guitartrainingkotlin.data.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import thomas.guitartrainingkotlin.data.db.GuitarTrainingDatabase
import thomas.guitartrainingkotlin.data.db.dao.*
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object PersistenceModule {

    lateinit var database: GuitarTrainingDatabase

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context)=
        Room.databaseBuilder(
            context,
            GuitarTrainingDatabase::class.java,
            GuitarTrainingDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideUserDao(db: GuitarTrainingDatabase): UserDao {
        return db.getUserDao()
    }
//
//    @Provides
//    @Singleton
//    fun provideProgramDao(db: GuitarTrainingDatabase): ProgramDao {
//        return db.getProgramDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideExerciseDao(db: GuitarTrainingDatabase): ExerciseDao {
//        return db.getExerciseDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideSongDao(db: GuitarTrainingDatabase): SongDao {
//        return db.getSongDao()
//    }
//
//    @Provides
//    @Singleton
//    fun provideScoreDao(db: GuitarTrainingDatabase): ScoreDao {
//        return db.getScoreDao()
//    }
}
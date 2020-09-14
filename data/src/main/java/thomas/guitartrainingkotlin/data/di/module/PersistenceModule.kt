package thomas.guitartrainingkotlin.data.di.module

import android.content.Context
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

    @Provides
    @Singleton
    fun provideUserDao(@ApplicationContext appContext: Context): UserDao {
        return GuitarTrainingDatabase.getInstance(appContext).userDao
    }

    @Provides
    @Singleton
    fun provideProgramDao(@ApplicationContext appContext: Context): ProgramDao {
        return GuitarTrainingDatabase.getInstance(appContext).programDao
    }

    @Provides
    @Singleton
    fun provideExerciseDao(@ApplicationContext appContext: Context): ExerciseDao {
        return GuitarTrainingDatabase.getInstance(appContext).exerciseDao
    }

    @Provides
    @Singleton
    fun provideSongDao(@ApplicationContext appContext: Context): SongDao {
        return GuitarTrainingDatabase.getInstance(appContext).songDao
    }

    @Provides
    @Singleton
    fun provideScoreDao(@ApplicationContext appContext: Context): ScoreDao {
        return GuitarTrainingDatabase.getInstance(appContext).scoreDao
    }
}
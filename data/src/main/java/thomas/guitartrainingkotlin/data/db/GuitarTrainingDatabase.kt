package thomas.guitartrainingkotlin.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import thomas.guitartrainingkotlin.data.db.converter.ExerciseConverter
import thomas.guitartrainingkotlin.data.db.dao.*
import thomas.guitartrainingkotlin.data.db.entity.*

@Database(
    version = 1,
    entities = [
        UserDBEntity::class,
        ProgramDBEntity::class,
        ExerciseDBEntity::class,
        SongDBEntity::class,
        ScoreDBEntity::class
    ],
    exportSchema = false
)
@TypeConverters(
    ExerciseConverter::class
)
abstract class GuitarTrainingDatabase : RoomDatabase() {

    /**
     * Connects the database to the DAO.
     */
    abstract val userDao: UserDao
    abstract val programDao: ProgramDao
    abstract val exerciseDao: ExerciseDao
    abstract val songDao: SongDao
    abstract val scoreDao: ScoreDao

    companion object {

        private const val DATABASE_NAME = "GuitarTrainingDb"

        @Volatile
        private var INSTANCE: GuitarTrainingDatabase? = null

        fun getInstance(context: Context): GuitarTrainingDatabase {
            // Multiple threads can ask for the database at the same time, ensure we only initialize
            // it once by using synchronized. Only one thread may enter a synchronized block at a
            // time.
            synchronized(this) {

                // Copy the current value of INSTANCE to a local variable so Kotlin can smart cast.
                // Smart cast is only available to local variables.
                var instance = INSTANCE

                // If instance is `null` make a new database instance.
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GuitarTrainingDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()   // No migration
                        .build()
                    // Assign INSTANCE to the newly created database.
                    INSTANCE = instance
                }

                // Return instance; smart cast to be non-null.
                return instance
            }
        }

    }
}
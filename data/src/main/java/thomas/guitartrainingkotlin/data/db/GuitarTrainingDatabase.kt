package thomas.guitartrainingkotlin.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import thomas.guitartrainingkotlin.data.db.converter.ExerciseConverter
import thomas.guitartrainingkotlin.data.db.dao.*
import thomas.guitartrainingkotlin.data.db.entity.*

@Database(
    version = 1,
    entities = [
        UserDBEntity::class
    ]
)
abstract class GuitarTrainingDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "GuitarTrainingDb"
    }

    abstract fun getUserDao(): UserDao
}
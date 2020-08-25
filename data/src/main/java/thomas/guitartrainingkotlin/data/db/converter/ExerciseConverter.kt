package thomas.guitartrainingkotlin.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import thomas.guitartrainingkotlin.data.db.entity.ExerciseDBEntity

class ExerciseConverter {

    @TypeConverter
    fun listToJson(value: List<ExerciseDBEntity>?): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<ExerciseDBEntity>? {

        val objects =
            Gson().fromJson(value, Array<ExerciseDBEntity>::class.java) as Array<ExerciseDBEntity>
        return objects.toList()
    }

}
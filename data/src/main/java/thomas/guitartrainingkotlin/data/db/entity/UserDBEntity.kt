package thomas.guitartrainingkotlin.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserDBEntity(
    @PrimaryKey
    val userId: String,
    val userPseudo: String = "",
    val userEmail: String = ""
)
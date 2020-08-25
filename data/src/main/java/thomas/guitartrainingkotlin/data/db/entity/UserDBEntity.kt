package thomas.guitartrainingkotlin.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class UserDBEntity(

    @PrimaryKey
    var userId: String = "",
    var userPseudo: String = "",
    var userEmail: String? = ""

)
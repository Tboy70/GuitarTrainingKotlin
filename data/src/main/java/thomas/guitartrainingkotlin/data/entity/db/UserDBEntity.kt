package thomas.guitartrainingkotlin.data.entity.db

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import thomas.guitartrainingkotlin.data.manager.db.DBManagerImpl

@Table(database = DBManagerImpl::class)
data class UserDBEntity(

    @PrimaryKey
    var userId: String? = "",

    @Column
    var userPseudo: String = "",

    @Column
    var userEmail: String? = ""
)
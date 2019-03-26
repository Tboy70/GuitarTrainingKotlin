package thomas.guitartrainingkotlin.data.entity.db

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import thomas.guitartrainingkotlin.data.manager.db.DBManagerImpl

@Table(database = DBManagerImpl::class)
data class ScoreDBEntity(

    @PrimaryKey
    var idScore: String = "",

    @Column
    var valueScore: Float = 0f,

    @Column
    var dateScore: String = "",

    @Column
    var idSong: String = ""

) {

    override fun toString(): String {
        return "ScoreDBEntity(idScore='$idScore', valueScore=$valueScore, dateScore='$dateScore', idSong='$idSong')"
    }
}
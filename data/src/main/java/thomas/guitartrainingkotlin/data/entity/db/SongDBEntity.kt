package thomas.guitartrainingkotlin.data.entity.db

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import thomas.guitartrainingkotlin.data.manager.db.DBManagerImpl
import thomas.guitartrainingkotlin.domain.values.InstrumentModeValues

@Table(database = DBManagerImpl::class)
data class SongDBEntity(

    @PrimaryKey
    var idSong: String = "",

    @Column
    var titleSong: String = "",

    @Column
    var artistSong: String = "",

    @Column
    var averageScoreSong: Float = 0.0f,

    @Column
    var totalScoreSong: Int = 0,

    @Column
    var nbPlay: Int = 0,

    @Column
    var lastPlay: String = "",

    @Column
    var userId: String = "",

    @Column
    var idInstrument: Int = InstrumentModeValues.INSTRUMENT_MODE_GUITAR
) {

    override fun toString(): String {
        return "SongDBEntity(idSong='$idSong', titleSong='$titleSong', artistSong='$artistSong', averageScoreSong=$averageScoreSong, totalScoreSong=$totalScoreSong, nbPlay=$nbPlay, lastPlay='$lastPlay', userId='$userId', idInstrument=$idInstrument)"
    }
}
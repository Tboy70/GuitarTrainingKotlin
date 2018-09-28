package thomas.example.com.guitarTrainingKotlin.ui.objectwrapper

import thomas.example.com.model.Song
import java.io.Serializable

class SongObjectWrapper(@Transient val song: Song) : Serializable
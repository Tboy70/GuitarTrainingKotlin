package thomas.example.com.data.module

import io.reactivex.Observable
import thomas.example.com.data.entity.remote.exercise.ExerciseRemoteEntity
import thomas.example.com.data.entity.remote.program.ProgramRemoteEntity
import thomas.example.com.data.entity.remote.song.SongRemoteEntity
import thomas.example.com.data.entity.remote.user.UserRemoteEntity

interface ApiModule {

    fun connectUser(userRemoteEntity: UserRemoteEntity): Observable<UserRemoteEntity>

    fun retrieveUserById(idUser: String): Observable<UserRemoteEntity>

    fun createNewUser(userRemoteEntity: UserRemoteEntity): Observable<String>

    fun retrieveProgramsListByUserId(idUser: String, instrumentModeValue: Int): Observable<List<ProgramRemoteEntity>>

    fun retrieveProgramFromId(idProgram: String): Observable<ProgramRemoteEntity>

    fun createProgram(programRemoteEntity: ProgramRemoteEntity): Observable<String>

    fun createExercise(listRemoteEntities: List<ExerciseRemoteEntity>): Observable<Boolean>

    fun updateProgram(programRemoteEntity: ProgramRemoteEntity): Observable<Boolean>

    fun updateExercise(exerciseRemoteEntities: List<ExerciseRemoteEntity>): Observable<Boolean>

    fun removeProgram(idProgram: String): Observable<Boolean>

    fun removeExercises(exercisesRemoteEntitiesToBeRemoved: List<ExerciseRemoteEntity>): Observable<Boolean>

    fun retrieveSongsListByUserId(idUser: String): Observable<List<SongRemoteEntity>>

    fun retrieveSongFromId(idSong: String): Observable<SongRemoteEntity>

    fun createSong(songRemoteEntity: SongRemoteEntity): Observable<String>

    fun removeSong(idSong: String): Observable<Boolean>
}
package thomas.example.com.data.module

import io.reactivex.Observable
import thomas.example.com.data.entity.remote.ExerciseRemoteEntity
import thomas.example.com.data.entity.remote.ProgramRemoteEntity
import thomas.example.com.data.entity.remote.UserRemoteEntity

interface ApiModule {

    fun connectUser(userRemoteEntity: UserRemoteEntity): Observable<UserRemoteEntity>

    fun retrieveProgramsListByUserId(idUser: String): Observable<List<ProgramRemoteEntity>>

    fun retrieveProgramFromId(idProgram: String): Observable<ProgramRemoteEntity>

    fun createProgram(programRemoteEntity: ProgramRemoteEntity): Observable<String>

    fun createExercise(listRemoteEntities: List<ExerciseRemoteEntity>): Observable<Boolean>

    fun updateProgram(programRemoteEntity: ProgramRemoteEntity): Observable<Boolean>

    fun updateExercise(exerciseRemoteEntities: List<ExerciseRemoteEntity>): Observable<Boolean>

    fun removeProgram(idProgram: String): Observable<Boolean>

    fun removeExercises(exercisesRemoteEntitiesToBeRemoved: List<ExerciseRemoteEntity>): Observable<Boolean>

    fun retrieveUserById(idUser: String): Observable<UserRemoteEntity>

    fun createNewUser(userRemoteEntity: UserRemoteEntity): Observable<String>
}
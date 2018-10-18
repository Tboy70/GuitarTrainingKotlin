package thomas.example.com.repository

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import thomas.example.com.model.Exercise
import thomas.example.com.model.Program

interface ProgramRepository {

    fun retrieveProgramsListByUserId(idUser: String): Observable<List<Program>>

    fun retrieveProgramById(idProgram: String): Single<Program>

    fun createProgram(program: Program, exercisesList: List<Exercise>): Completable

    fun updateProgram(program: Program, exercisesToBeRemoved: List<Exercise>): Completable

    fun removeProgram(idProgram: String): Observable<Boolean>
}
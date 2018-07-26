package thomas.example.com.repository

import io.reactivex.Observable
import thomas.example.com.model.Program

interface ProgramRepository {

    fun retrieveProgramsListByUserId(idUser: String): Observable<List<Program>>

    fun retrieveProgramById(idProgram: String): Observable<Program>
}
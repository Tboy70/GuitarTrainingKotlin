package thomas.example.com.repository

import io.reactivex.Observable
import thomas.example.com.model.Program

interface ProgramRepository {

    fun retrieveProgramsListByUserId(userId: String): Observable<List<Program>>
}
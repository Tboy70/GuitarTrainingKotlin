package thomas.guitartrainingkotlin.domain.repository

import io.reactivex.Completable
import io.reactivex.Single
import thomas.guitartrainingkotlin.domain.model.Exercise
import thomas.guitartrainingkotlin.domain.model.Program

interface ProgramRepository {

    fun retrieveProgramListByUserId(userId: String): Single<List<Program>>

    fun retrieveProgramById(idProgram: String): Single<Program>

    fun createProgram(program: Program, exercisesList: List<Exercise>): Completable

    fun updateProgram(program: Program, exercisesToBeRemoved: List<Exercise>): Completable

    fun removeProgram(idProgram: String): Completable
}
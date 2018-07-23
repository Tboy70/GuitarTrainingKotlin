package thomas.example.com.data.repository

import io.reactivex.Observable
import thomas.example.com.data.mapper.ProgramEntityDataMapper
import thomas.example.com.data.repository.client.APIClient
import thomas.example.com.data.repository.client.ProgramClient
import thomas.example.com.model.Program
import thomas.example.com.repository.ProgramRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramRepositoryImpl @Inject constructor(private val programClient: ProgramClient,
                                                private val apiClient: APIClient,
                                                private val programEntityDataMapper: ProgramEntityDataMapper) : ProgramRepository {

    override fun retrieveProgramsListByUserId(userId: String): Observable<List<Program>> {
        return Observable.defer {
            apiClient.retrieveProgramsListByUserId(userId).map {
                programEntityDataMapper.transformListEntitiesToListModels(it)
            }
        }
    }
}
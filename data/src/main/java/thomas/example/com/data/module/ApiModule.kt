package thomas.example.com.data.module

import io.reactivex.Observable
import thomas.example.com.data.entity.remote.ProgramRemoteEntity
import thomas.example.com.data.entity.remote.UserRemoteEntity

interface ApiModule {

    fun connectUser(userRemoteEntity: UserRemoteEntity): Observable<UserRemoteEntity>

    fun retrieveProgramsListByUserId(userId: String): Observable<List<ProgramRemoteEntity>>
}
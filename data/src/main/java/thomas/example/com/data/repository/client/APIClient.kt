package thomas.example.com.data.repository.client

import io.reactivex.Observable
import thomas.example.com.data.entity.UserEntity
import thomas.example.com.data.mapper.remote.ExerciseRemoteEntityDataMapper
import thomas.example.com.data.mapper.remote.ProgramRemoteEntityDataMapper
import thomas.example.com.data.mapper.remote.UserRemoteEntityDataMapper
import thomas.example.com.data.module.ApiModule
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class APIClient @Inject constructor(private val apiModule: ApiModule,
                                    private val programRemoteEntityDataMapper: ProgramRemoteEntityDataMapper,
                                    private val exerciseRemoteEntityDataMapper: ExerciseRemoteEntityDataMapper,
                                    private val userRemoteEntityDataMapper: UserRemoteEntityDataMapper) {

    fun connectUser(userEntity: UserEntity): Observable<UserEntity>? {
        return apiModule.connectUser(userRemoteEntityDataMapper.transformEntityToRemoteEntity(userEntity))
                .map {
                    userRemoteEntityDataMapper.transformRemoteEntityToEntity(it)
                }
    }
}
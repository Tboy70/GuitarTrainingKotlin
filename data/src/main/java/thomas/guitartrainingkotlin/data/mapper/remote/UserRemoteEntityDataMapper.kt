package thomas.guitartrainingkotlin.data.mapper.remote

import thomas.guitartrainingkotlin.data.entity.UserEntity
import thomas.guitartrainingkotlin.data.entity.remote.user.UserRemoteEntity
import thomas.guitartrainingkotlin.data.exception.mapper.DataMappingException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteEntityDataMapper @Inject constructor() {

    @Throws(DataMappingException::class)
    fun transformToEntity(userRemoteEntity: UserRemoteEntity): UserEntity {
        try {
            return UserEntity(
                userId = userRemoteEntity.userId,
                userPseudo = userRemoteEntity.userPseudo,
                userEmail = userRemoteEntity.userEmail,
                userPassword = userRemoteEntity.userPassword
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToEntity(userRemoteEntityList: List<UserRemoteEntity>) =
        userRemoteEntityList.mapNotNull { userRemoteEntity ->
            try {
                transformToEntity(userRemoteEntity)
            } catch (e: DataMappingException) {
                null
            }
        }

    @Throws(DataMappingException::class)
    fun transformFromEntity(userEntity: UserEntity): UserRemoteEntity {
        try {
            return UserRemoteEntity(
                userId = userEntity.userId,
                userPseudo = userEntity.userPseudo,
                userEmail = userEntity.userEmail,
                userPassword = userEntity.userPassword
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformFromEntity(userEntityList: List<UserEntity>) = userEntityList.mapNotNull { userEntity ->
        try {
            transformFromEntity(userEntity)
        } catch (e: DataMappingException) {
            null
        }
    }
}
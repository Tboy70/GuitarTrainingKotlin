package thomas.guitartrainingkotlin.data.mapper

import thomas.guitartrainingkotlin.data.entity.UserEntity
import thomas.guitartrainingkotlin.data.exception.mapper.DataMappingException
import thomas.guitartrainingkotlin.domain.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserEntityDataMapper @Inject constructor() {

    fun transformFromEntity(userEntityList: List<UserEntity>) = userEntityList.mapNotNull { userEntity ->
        try {
            transformFromEntity(userEntity)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformFromEntity(userEntity: UserEntity): User {
        try {
            return User(
                userId = userEntity.userId,
                userPseudo = userEntity.userPseudo,
                userEmail = userEntity.userEmail,
                userPassword = userEntity.userPassword
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToEntity(userList: List<User>) = userList.mapNotNull { user ->
        try {
            transformToEntity(user)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformToEntity(user: User): UserEntity {
        try {
            return UserEntity(
                userId = user.userId,
                userPseudo = user.userPseudo,
                userEmail = user.userEmail,
                userPassword = user.userPassword
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }
}
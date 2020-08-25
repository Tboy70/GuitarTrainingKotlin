package thomas.guitartrainingkotlin.data.mapper.db

import thomas.guitartrainingkotlin.data.db.entity.UserDBEntity
import thomas.guitartrainingkotlin.data.entity.UserEntity
import thomas.guitartrainingkotlin.data.exception.mapper.DataMappingException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDBEntityDataMapper @Inject constructor() {

    fun transformFromDB(userDBEntityList: List<UserDBEntity>) =
        userDBEntityList.mapNotNull { userDBEntity ->
            try {
                transformFromDB(userDBEntity)
            } catch (e: DataMappingException) {
                null
            }
        }

    @Throws(DataMappingException::class)
    fun transformFromDB(userDBEntity: UserDBEntity): UserEntity {
        try {
            return UserEntity(
                userId = userDBEntity.userId,
                userPseudo = userDBEntity.userPseudo,
                userEmail = userDBEntity.userEmail
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }

    fun transformToDB(userEntityList: List<UserEntity>) = userEntityList.mapNotNull { userEntity ->
        try {
            transformToDB(userEntity)
        } catch (e: DataMappingException) {
            null
        }
    }

    @Throws(DataMappingException::class)
    fun transformToDB(userEntity: UserEntity): UserDBEntity {
        try {
            return UserDBEntity(
                userId = userEntity.userId?:"What", // TODO
                userPseudo = userEntity.userPseudo,
                userEmail = userEntity.userEmail
            )
        } catch (e: Exception) {
            throw DataMappingException()
        }
    }
}
package thomas.example.com.data.mapper.remote

import thomas.example.com.data.entity.UserEntity
import thomas.example.com.data.entity.remote.user.UserRemoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteEntityDataMapper @Inject constructor() {

    fun transformRemoteEntityToEntity(userRemoteEntity: UserRemoteEntity): UserEntity {
        return UserEntity(
            userRemoteEntity.idUser,
            userRemoteEntity.pseudoUser,
            userRemoteEntity.emailUser,
            userRemoteEntity.passwordUser
        )
    }

    fun transformListRemoteEntitiesToListEntities(userRemoteEntityList: List<UserRemoteEntity>): List<UserEntity> {
        val userEntityList: MutableList<UserEntity> = mutableListOf()

        for (userRemoteEntity: UserRemoteEntity in userRemoteEntityList) {
            userEntityList.add(transformRemoteEntityToEntity(userRemoteEntity))
        }
        return userEntityList
    }

    fun transformEntityToRemoteEntity(userEntity: UserEntity): UserRemoteEntity {
        return UserRemoteEntity(
            userEntity.idUser,
            userEntity.pseudoUser,
            userEntity.emailUser,
            userEntity.passwordUser
        )
    }

    fun transformListEntitiesToListRemoteEntities(userEntityList: List<UserEntity>): List<UserRemoteEntity> {
        val userRemoteEntityList: MutableList<UserRemoteEntity> = mutableListOf()

        for (userEntity: UserEntity in userEntityList) {
            userRemoteEntityList.add(transformEntityToRemoteEntity(userEntity))
        }
        return userRemoteEntityList
    }
}
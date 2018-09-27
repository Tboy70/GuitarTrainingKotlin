package thomas.example.com.data.mapper.remote

import thomas.example.com.data.entity.UserEntity
import thomas.example.com.data.entity.remote.user.UserRemoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteEntityDataMapper @Inject constructor() {

    fun transformRemoteEntityToEntity(userRemoteEntity: UserRemoteEntity): UserEntity {
        val userEntity = UserEntity()
        userEntity.idUser = userRemoteEntity.idUser
        userEntity.pseudoUser = userRemoteEntity.pseudoUser
        userEntity.emailUser = userRemoteEntity.emailUser
        userEntity.passwordUser = userRemoteEntity.passwordUser
        return userEntity
    }

    fun transformListRemoteEntitiesToListEntities(userRemoteEntityList: List<UserRemoteEntity>): List<UserEntity> {
        val userEntityList: MutableList<UserEntity> = mutableListOf()

        for (userRemoteEntity: UserRemoteEntity in userRemoteEntityList) {
            userEntityList.add(transformRemoteEntityToEntity(userRemoteEntity))
        }
        return userEntityList
    }

    fun transformEntityToRemoteEntity(userEntity: UserEntity): UserRemoteEntity {
        val userRemoteEntity = UserRemoteEntity()
        userRemoteEntity.idUser = userEntity.idUser
        userRemoteEntity.pseudoUser = userEntity.pseudoUser
        userRemoteEntity.emailUser = userEntity.emailUser
        userRemoteEntity.passwordUser = userEntity.passwordUser
        return userRemoteEntity
    }

    fun transformListEntitiesToListRemoteEntities(userEntityList: List<UserEntity>): List<UserRemoteEntity> {
        val userRemoteEntityList: MutableList<UserRemoteEntity> = mutableListOf()

        for (userEntity: UserEntity in userEntityList) {
            userRemoteEntityList.add(transformEntityToRemoteEntity(userEntity))
        }
        return userRemoteEntityList
    }
}
package thomas.example.com.data.mapper.remote

import thomas.example.com.data.entity.UserEntity
import thomas.example.com.data.entity.remote.UserRemoteEntity
import javax.inject.Singleton

@Singleton
class UserRemoteEntityDataMapper {

    fun transformUserRemoteEntityToUserEntity(userRemoteEntity: UserRemoteEntity): UserEntity {
        val userEntity = UserEntity()
        userEntity.idUser = userRemoteEntity.idUser
        userEntity.pseudoUser = userRemoteEntity.pseudoUser
        userEntity.passwordUser = userRemoteEntity.passwordUser
        return userEntity
    }

    fun transformListUserRemoteEntitiesToUserEntities(userRemoteEntityList: List<UserRemoteEntity>): List<UserEntity> {
        val userEntityList: MutableList<UserEntity> = mutableListOf()

        for (userRemoteEntity: UserRemoteEntity in userRemoteEntityList) {
            userEntityList.add(transformUserRemoteEntityToUserEntity(userRemoteEntity))
        }
        return userEntityList
    }

    fun transformUserEntityToUserRemoteEntity(userEntity: UserEntity): UserRemoteEntity {
        val userRemoteEntity = UserRemoteEntity()
        userRemoteEntity.idUser = userEntity.idUser
        userRemoteEntity.pseudoUser = userEntity.pseudoUser
        userRemoteEntity.passwordUser = userEntity.passwordUser
        return userRemoteEntity
    }

    fun transformListUserEntitiesToUserRemoteEntities(userEntityList: List<UserEntity>): List<UserRemoteEntity> {
        val userRemoteEntityList: MutableList<UserRemoteEntity> = mutableListOf()

        for (userEntity: UserEntity in userEntityList) {
            userRemoteEntityList.add(transformUserEntityToUserRemoteEntity(userEntity))
        }
        return userRemoteEntityList
    }
}
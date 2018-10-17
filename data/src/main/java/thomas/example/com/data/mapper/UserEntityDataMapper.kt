package thomas.example.com.data.mapper

import thomas.example.com.data.entity.UserEntity
import thomas.example.com.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserEntityDataMapper @Inject constructor() {

    fun transformEntityToModel(userEntity: UserEntity): User {
        return User(userEntity.idUser, userEntity.pseudoUser, userEntity.emailUser, userEntity.passwordUser)
    }

    fun transformListEntitiesToListModels(userEntityList: List<UserEntity>): List<User> {
        val userList: MutableList<User> = mutableListOf()

        for (userEntity: UserEntity in userEntityList) {
            userList.add(transformEntityToModel(userEntity))
        }
        return userList
    }

    fun transformModelToEntity(user: User): UserEntity {
        return UserEntity(
            user.idUser,
            user.pseudoUser,
            user.emailUser,
            user.passwordUser
        )
    }

    fun transformListModelsToListEntities(userList: List<User>): List<UserEntity> {
        val userEntityList: MutableList<UserEntity> = mutableListOf()

        for (user: User in userList) {
            userEntityList.add(transformModelToEntity(user))
        }
        return userEntityList
    }
}
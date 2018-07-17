package thomas.example.com.data.mapper

import thomas.example.com.data.entity.UserEntity
import thomas.example.com.model.User
import javax.inject.Singleton

@Singleton
class UserEntityDataMapper {

    fun transformUserEntityToUserModel(userEntity: UserEntity): User {
        val user = User()
        user.idUser = userEntity.idUser
        user.pseudoUser = userEntity.pseudoUser
        user.passwordUser = userEntity.passwordUser
        return user
    }

    fun transformListUserEntitiesToUserModels(userEntityList: List<UserEntity>): List<User> {
        val userList: MutableList<User> = mutableListOf()

        for (userEntity: UserEntity in userEntityList) {
            userList.add(transformUserEntityToUserModel(userEntity))
        }
        return userList
    }

    fun transformUserToUserEntity(user: User): UserEntity {
        val userEntity = UserEntity()
        userEntity.idUser = user.idUser
        userEntity.pseudoUser = user.pseudoUser
        userEntity.passwordUser = user.passwordUser
        return userEntity
    }

    fun transformListUsersToUserEntities(userList: List<User>): List<UserEntity> {
        val userEntityList: MutableList<UserEntity> = mutableListOf()

        for (user: User in userList) {
            userEntityList.add(transformUserToUserEntity(user))
        }
        return userEntityList
    }
}
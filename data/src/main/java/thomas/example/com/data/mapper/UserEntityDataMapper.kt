package thomas.example.com.data.mapper

import thomas.example.com.data.entity.UserEntity
import thomas.example.com.model.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserEntityDataMapper @Inject constructor() {

    fun transformEntityToModel(userEntity: UserEntity): User {
        val user = User()
        user.idUser = userEntity.idUser
        user.pseudoUser = userEntity.pseudoUser
        user.passwordUser = userEntity.passwordUser
        return user
    }

    fun transformListEntitiesToListModels(userEntityList: List<UserEntity>): List<User> {
        val userList: MutableList<User> = mutableListOf()

        for (userEntity: UserEntity in userEntityList) {
            userList.add(transformEntityToModel(userEntity))
        }
        return userList
    }

    fun transformModelToEntity(user: User): UserEntity {
        val userEntity = UserEntity()
        userEntity.idUser = user.idUser
        userEntity.pseudoUser = user.pseudoUser
        userEntity.passwordUser = user.passwordUser
        return userEntity
    }

    fun transformListModelsToListEntities(userList: List<User>): List<UserEntity> {
        val userEntityList: MutableList<UserEntity> = mutableListOf()

        for (user: User in userList) {
            userEntityList.add(transformModelToEntity(user))
        }
        return userEntityList
    }
}
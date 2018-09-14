package thomas.example.com.utils

class ConstantErrors : Throwable() {
    companion object {

        const val ERROR_CONNECT_USER = "thomas.example.com.utils.ERROR_CONNECT_USER"
        const val ERROR_CREATION_USER = "thomas.example.com.utils.ERROR_CREATION_USER"
        const val ERROR_RETRIEVE_USER = "thomas.example.com.utils.ERROR_RETRIEVE_USER"

        const val ERROR_RETRIEVE_PROGRAM = "thomas.example.com.utils.ERROR_RETRIEVE_PROGRAM"
        const val ERROR_RETRIEVE_PROGRAMS = "thomas.example.com.utils.ERROR_RETRIEVE_PROGRAMS"
        const val ERROR_CREATION_PROGRAM = "thomas.example.com.utils.ERROR_CREATION_PROGRAM"
        const val ERROR_UPDATE_PROGRAM = "thomas.example.com.utils.ERROR_UPDATE_PROGRAM"
        const val ERROR_REMOVE_PROGRAM = "thomas.example.com.utils.ERROR_REMOVE_PROGRAM"

        const val ERROR_CREATION_EXERCISE = "thomas.example.com.utils.ERROR_CREATION_EXERCISE"
        const val ERROR_UPDATE_EXERCISE = "thomas.example.com.utils.ERROR_UPDATE_EXERCISE"
        const val ERROR_REMOVE_EXERCISE = "thomas.example.com.utils.ERROR_REMOVE_EXERCISE"
    }
}
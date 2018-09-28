package thomas.example.com.guitarTrainingKotlin.utils

import android.content.Context
import retrofit2.HttpException
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.utils.ConstantErrors
import java.net.ConnectException

class ErrorUtils {

    companion object {
        private const val HTTP_EXCEPTION_401 = 401

        fun translateException(context: Context, throwable: Throwable): String {
            when (throwable) {
                is HttpException -> {
                    when (throwable.code()) {
                        HTTP_EXCEPTION_401 -> if (throwable.message() == context.getString(R.string.wrong_login_error_value_from_API)) {
                            return context.getString(R.string.snackbar_error_wrong_login)
                        }
                        else -> return context.getString(R.string.snackbar_error_unknown_error)
                    }
                }
                is ConnectException -> return context.getString(R.string.snackbar_error_connection_problem)
                is Exception -> {
                    return when (throwable.message) {
                        ConstantErrors.ERROR_CONNECT_USER -> context.getString(R.string.error_connect_user)
                        ConstantErrors.ERROR_CREATION_USER -> context.getString(R.string.error_creation_user)
                        ConstantErrors.ERROR_RETRIEVE_USER -> context.getString(R.string.error_retrieve_user)
                        ConstantErrors.ERROR_RETRIEVE_PROGRAM -> context.getString(R.string.error_retrieve_program)
                        ConstantErrors.ERROR_RETRIEVE_PROGRAMS -> context.getString(R.string.error_retrieve_programs)
                        ConstantErrors.ERROR_CREATION_PROGRAM -> context.getString(R.string.error_creation_program)
                        ConstantErrors.ERROR_UPDATE_PROGRAM -> context.getString(R.string.error_update_program)
                        ConstantErrors.ERROR_REMOVE_PROGRAM -> context.getString(R.string.error_remove_program)
                        ConstantErrors.ERROR_CREATION_EXERCISE -> context.getString(R.string.error_creation_exercises)
                        ConstantErrors.ERROR_UPDATE_EXERCISE -> context.getString(R.string.error_update_exercise)
                        ConstantErrors.ERROR_REMOVE_EXERCISE -> context.getString(R.string.error_remove_exercise)
                        ConstantErrors.ERROR_RETRIEVE_SONG -> context.getString(R.string.error_retrieve_song)
                        ConstantErrors.ERROR_RETRIEVE_SONGS -> context.getString(R.string.error_retrieve_songs)
                        ConstantErrors.ERROR_CREATION_SONG -> context.getString(R.string.error_creation_song)
                        ConstantErrors.ERROR_UPDATE_SONG -> context.getString(R.string.error_update_song)
                        ConstantErrors.ERROR_REMOVE_SONG -> context.getString(R.string.error_remove_song)
                        ConstantErrors.ERROR_SEND_FEEDBACK_SONG -> context.getString(R.string.error_send_feedback_song)
                        else -> context.getString(R.string.snackbar_error_unknown_error)
                    }

                }
                else -> return context.getString(R.string.snackbar_error_unknown_error)
            }
            return throwable.message.toString()
        }
    }

}
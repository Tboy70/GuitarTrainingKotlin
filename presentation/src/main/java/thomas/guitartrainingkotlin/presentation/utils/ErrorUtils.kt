package thomas.guitartrainingkotlin.presentation.utils

import android.content.Context
import retrofit2.HttpException
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.data.exception.ProgramNotFoundException
import thomas.guitartrainingkotlin.data.exception.SongNotFoundException
import thomas.guitartrainingkotlin.domain.utils.ConstantErrors
import java.net.ConnectException
import java.net.UnknownHostException

object ErrorUtils {

    fun translateException(context: Context, throwable: Throwable): String {
        when (throwable) {
            is HttpException -> {
                return throwable.message()
            }
            is ConnectException -> return context.getString(R.string.error_connection_problem)
            is UnknownHostException -> return context.getString(R.string.error_server_connection_problem)
            is SongNotFoundException -> return context.getString(R.string.error_song_not_found)
            is ProgramNotFoundException -> return context.getString(R.string.error_program_not_found)
            is Exception -> {
                return when (throwable.message) {
                    ConstantErrors.ERROR_CONNECT_USER -> context.getString(R.string.error_connect_user)
                    ConstantErrors.ERROR_CREATION_USER -> context.getString(R.string.error_creation_user)
                    ConstantErrors.ERROR_RETRIEVE_USER -> context.getString(R.string.error_retrieve_user)
                    ConstantErrors.ERROR_REMOVE_USER -> context.getString(R.string.error_remove_user)
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
                    ConstantErrors.ERROR_RETRIEVE_SCORE_SONG_HISTORIC -> context.getString(R.string.error_retrieve_song_score_historic)
                    else -> throwable.message.toString()
                }

            }
            else -> return context.getString(R.string.snackbar_error_unknown_error)
        }
    }

}
package thomas.example.com.guitarTrainingKotlin.utils

import android.content.Context
import retrofit2.HttpException
import thomas.example.com.guitarTrainingKotlin.R
import java.net.ConnectException

class ErrorUtils {

    companion object {
        const val HTTP_EXCEPTION_401 = 401

        fun translateException(context: Context, throwable: Throwable) : String {
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
                else -> return context.getString(R.string.snackbar_error_unknown_error)
            }
            return context.getString(R.string.snackbar_error_unknown_error)
        }
    }

}
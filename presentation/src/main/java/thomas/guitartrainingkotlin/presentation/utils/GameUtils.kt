package thomas.guitartrainingkotlin.presentation.utils

import android.content.Context
import thomas.guitartrainingkotlin.R

object GameUtils {

    fun checkAnswer(randomNote: String, randomInterval: String, answer: String, context: Context) {
        findIntervalValue(context, randomInterval)
    }

    private fun findIntervalValue(context: Context, randomInterval: String) {
        val intervalArray = context.resources.getStringArray(R.array.list_interval)
        when {
            randomInterval == intervalArray[0] -> {

            }
            randomInterval == intervalArray[1] -> {

            }
            randomInterval == intervalArray[2] -> {

            }
            randomInterval == intervalArray[3] -> {

            }
            randomInterval == intervalArray[4] -> {

            }
            randomInterval == intervalArray[5] -> {

            }
            randomInterval == intervalArray[6] -> {

            }
            randomInterval == intervalArray[7] -> {

            }
            randomInterval == intervalArray[8] -> {

            }
            randomInterval == intervalArray[9] -> {

            }
            randomInterval == intervalArray[10] -> {

            }
            randomInterval == intervalArray[11] -> {

            }
            randomInterval == intervalArray[12] -> {

            }
            randomInterval == intervalArray[13] -> {

            }
            randomInterval == intervalArray[14] -> {

            }
            randomInterval == intervalArray[15] -> {

            }
        }


    }
}
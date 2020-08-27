package thomas.guitartrainingkotlin.data.entity.remote.song

import com.google.gson.annotations.SerializedName

data class ScoreFeedbackRemoteEntity(@SerializedName("scoreFeedback") val scoreFeedback: Int = 0)
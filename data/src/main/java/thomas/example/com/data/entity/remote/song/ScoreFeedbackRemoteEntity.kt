package thomas.example.com.data.entity.remote.song

import com.google.gson.annotations.SerializedName

data class ScoreFeedbackRemoteEntity(@SerializedName("scoreFeedback") var scoreFeedback: Int = 0)
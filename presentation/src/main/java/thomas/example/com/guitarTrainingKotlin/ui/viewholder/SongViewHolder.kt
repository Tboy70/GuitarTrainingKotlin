package thomas.example.com.guitarTrainingKotlin.ui.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_user_songs_list_item.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserSongsListAdapterListener
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.SongViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.DateTimeUtils

class SongViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    private val currentView: View = itemView

    fun bindSong(songViewDataWrapper: SongViewDataWrapper, userSongsListAdapterListener: UserSongsListAdapterListener) {
        view_user_songs_list_item_title.text = songViewDataWrapper.getTitleSong()
        view_user_songs_list_item_artist.text = songViewDataWrapper.getArtistSong()

        val averageScoreSong = songViewDataWrapper.getAverageScoreSong()
        val nbPlay = songViewDataWrapper.getNbPlay()
        view_user_songs_list_item_nb_play.text =
                String.format(context.getString(R.string.user_songs_list_nb_play), nbPlay)
        if ((averageScoreSong == 0.0f && nbPlay == 0) || nbPlay == 0) {
            view_user_songs_list_item_score.text = String.format(
                context.getString(R.string.user_songs_list_score),
                context.getString(R.string.const_na),
                ConstValues.EMPTY_STRING
            )
        } else {
            view_user_songs_list_item_score.text = String.format(
                context.getString(R.string.user_songs_list_score),
                (Math.round(averageScoreSong * 100.0) / 100.0).toString()
            )
        }

        val lastPlay = songViewDataWrapper.getLastPlay()
        if (lastPlay.isEmpty()) {
            view_user_songs_list_item_last_play.text = String.format(
                context.getString(R.string.user_songs_list_last_play),
                context.getString(R.string.never)
            )
        } else {
            val date = DateTimeUtils.formatDate(DateTimeUtils.FROM_API_FORMAT, DateTimeUtils.WANTED_FORMAT, lastPlay)
            view_user_songs_list_item_last_play.text =
                    String.format(context.getString(R.string.user_songs_list_last_play), date)
        }

        currentView.setOnClickListener {
            userSongsListAdapterListener.onSongClick(songViewDataWrapper.getIdSong())
        }
    }
}
package thomas.example.com.guitarTrainingKotlin.ui.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_user_songs_list_item.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserSongsListAdapterListener
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.SongObjectWrapper
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.DateTimeUtils

class SongViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    private val currentView: View = itemView

    fun bindSong(songObjectWrapper: SongObjectWrapper, userSongsListAdapterListener: UserSongsListAdapterListener) {
        view_user_songs_list_item_title.text = songObjectWrapper.song.titleSong
        view_user_songs_list_item_artist.text = songObjectWrapper.song.artistSong

        val averageScoreSong = songObjectWrapper.song.averageScoreSong
        val nbPlay = songObjectWrapper.song.nbPlay
        view_user_songs_list_item_nb_play.text = String.format(context.getString(R.string.user_songs_list_nb_play), nbPlay)
        if ((averageScoreSong == 0.0f && nbPlay == 0) || nbPlay == 0) {
            view_user_songs_list_item_score.text = String.format(context.getString(R.string.user_songs_list_score), context.getString(R.string.const_na), ConstValues.EMPTY_STRING)
        } else {
            view_user_songs_list_item_score.text = String.format(context.getString(R.string.user_songs_list_score), (Math.round(averageScoreSong * 100.0) / 100.0).toString())
        }

        val lastPlay = songObjectWrapper.song.lastPlay
        if (lastPlay.isEmpty()) {
            view_user_songs_list_item_last_play.text = String.format(context.getString(R.string.user_songs_list_last_play), context.getString(R.string.never))
        } else {
            val date = DateTimeUtils.formatDate(DateTimeUtils.FROM_API_FORMAT, DateTimeUtils.WANTED_FORMAT, lastPlay)
            view_user_songs_list_item_last_play.text = String.format(context.getString(R.string.user_songs_list_last_play), date)
        }

        currentView.setOnClickListener {
            userSongsListAdapterListener.onSongClick(songObjectWrapper.song.idSong)
        }
    }
}
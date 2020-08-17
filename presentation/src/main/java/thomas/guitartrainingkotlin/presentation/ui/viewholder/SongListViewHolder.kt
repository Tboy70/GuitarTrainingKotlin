package thomas.guitartrainingkotlin.presentation.ui.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_user_songs_list_item.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.utils.DateTimeUtils
import thomas.guitartrainingkotlin.presentation.view.datawrapper.SongViewDataWrapper

class SongListViewHolder(
    itemView: View,
    private val context: Context
) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    private val currentView: View = itemView

    fun bind(
        songViewDataWrapper: SongViewDataWrapper,
        onSongSelectedListener: (songId: String) -> Unit
    ) {
        view_user_songs_list_item_title.text = songViewDataWrapper.getTitleSong()
        view_user_songs_list_item_artist.text = songViewDataWrapper.getArtistSong()

        displayScoreSong(songViewDataWrapper)
        displayLastPlaySong(songViewDataWrapper)

        currentView.setOnClickListener {
            onSongSelectedListener(songViewDataWrapper.getId())
        }
    }

    private fun displayScoreSong(songViewDataWrapper: SongViewDataWrapper) {
        val averageScoreSong = songViewDataWrapper.getAverageScoreSong()
        val nbPlay = songViewDataWrapper.getNbPlay()
        view_user_songs_list_item_nb_play.text =
            String.format(context.getString(R.string.user_song_list_nb_play), nbPlay)

        (((averageScoreSong == 0.0f && nbPlay == 0) || nbPlay == 0)).let {
            if (it) {
                context.getString(R.string.const_na)
            } else {
                (Math.round(averageScoreSong * 100.0) / 100.0).toString()
            }
        }.let { infoToDisplay ->
            view_user_songs_list_item_score.text = String.format(
                context.getString(R.string.user_song_list_score),
                infoToDisplay
            )
        }
    }

    private fun displayLastPlaySong(songViewDataWrapper: SongViewDataWrapper) {
        songViewDataWrapper.getLastPlay().let { lastPlay ->
            if (lastPlay.isEmpty()) {
                context.getString(R.string.never)
            } else {
                DateTimeUtils.formatDate(
                    DateTimeUtils.FROM_API_FORMAT,
                    DateTimeUtils.WANTED_FORMAT,
                    lastPlay
                )
            }.let { infoToDisplay ->
                view_user_songs_list_item_last_play.text =
                    String.format(
                        context.getString(R.string.user_song_list_last_play),
                        infoToDisplay
                    )
            }
        }
    }
}
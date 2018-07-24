package thomas.example.com.guitarTrainingKotlin.ui.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_user_songs_list_item.*
import thomas.example.com.guitarTrainingKotlin.ui.adapter.UserSongsListAdapterListener
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.SongObjectWrapper

class SongViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView: View?
        get() = itemView

    private val currentView: View = itemView

    fun bindSong(songObjectWrapper: SongObjectWrapper, userSongsListAdapterListener: UserSongsListAdapterListener) {
        view_user_songs_list_item_title.text = songObjectWrapper.song.titleSong
        view_user_songs_list_item_artist.text = songObjectWrapper.song.artistSong

        currentView.setOnClickListener {
            userSongsListAdapterListener.onSongClick(songObjectWrapper.song.idSong)
        }
    }
}
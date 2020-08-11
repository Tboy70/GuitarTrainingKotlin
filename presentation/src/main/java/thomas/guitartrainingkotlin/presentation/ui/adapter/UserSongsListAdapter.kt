package thomas.guitartrainingkotlin.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.FragmentScoped
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.ui.viewholder.SongListViewHolder
import thomas.guitartrainingkotlin.presentation.view.datawrapper.SongViewDataWrapper
import javax.inject.Inject

@FragmentScoped
class UserSongsListAdapter @Inject constructor(
    private val context: Context
) : RecyclerView.Adapter<SongListViewHolder>() {

    var onSongSelectedListener: (songId: String) -> Unit = {}

    private val songList = mutableListOf<SongViewDataWrapper>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SongListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_user_songs_list_item,
                parent,
                false
            ), context
        )


    override fun onBindViewHolder(holder: SongListViewHolder, position: Int) {
        holder.bind(songList[position], onSongSelectedListener)
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    fun updateSongList(songList: List<SongViewDataWrapper>) {
        this.songList.clear()
        this.songList.addAll(songList)
        notifyDataSetChanged()
    }
}
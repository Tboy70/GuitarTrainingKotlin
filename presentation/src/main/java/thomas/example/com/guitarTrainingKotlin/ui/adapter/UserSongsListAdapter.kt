package thomas.example.com.guitarTrainingKotlin.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.SongViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.ui.viewholder.SongViewHolder
import javax.inject.Inject

class UserSongsListAdapter @Inject constructor(activity: BaseActivity) : RecyclerView.Adapter<SongViewHolder>() {

    private val activity: Activity
    private val songsList: MutableList<SongViewDataWrapper>
    private lateinit var userSongsListAdapterListener: UserSongsListAdapterListener

    init {
        this.activity = activity
        this.songsList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_user_songs_list_item, parent, false)
        return SongViewHolder(view, activity)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bindSong(songsList[position], userSongsListAdapterListener)
    }

    override fun getItemCount(): Int {
        return songsList.size
    }

    fun setUserSongsListAdapter(userSongsListAdapterListener: UserSongsListAdapterListener) {
        this.userSongsListAdapterListener = userSongsListAdapterListener
    }

    fun updateSongsList(
            songs: List<SongViewDataWrapper>
    ) {
        clearList()
        songsList.addAll(songs)
        notifyDataSetChanged()
    }

    private fun clearList() {
        songsList.clear()
        notifyDataSetChanged()
    }
}
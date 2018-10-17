package thomas.example.com.guitarTrainingKotlin.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.BaseActivity
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.ProgramObjectWrapper
import thomas.example.com.guitarTrainingKotlin.ui.viewholder.ProgramViewHolder
import thomas.example.com.model.Program
import javax.inject.Inject

class UserProgramsListAdapter @Inject constructor(activity: BaseActivity) : RecyclerView.Adapter<ProgramViewHolder>() {

    private val activity: Activity
    private val programList: MutableList<Program>
    private lateinit var userProgramsListAdapterListener: UserProgramsListAdapterListener

    init {
        this.activity = activity
        this.programList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_user_programs_list_item, parent, false)
        return ProgramViewHolder(view, activity)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        holder.bindProgram(ProgramObjectWrapper(programList[position]), userProgramsListAdapterListener)
    }

    override fun getItemCount(): Int {
        return programList.size
    }

    fun setUserProgramsListAdapter(userProgramsListAdapterListener: UserProgramsListAdapterListener) {
        this.userProgramsListAdapterListener = userProgramsListAdapterListener
    }

    fun updateProgramsList(programs: List<Program>?) {
        clearList()
        if (programs != null) {
            programList.addAll(programs)
            notifyDataSetChanged()
        }
    }

    private fun clearList() {
        programList.clear()
        notifyDataSetChanged()
    }
}
package thomas.example.com.guitarTrainingKotlin.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.di.annotation.PerFragment
import thomas.example.com.guitarTrainingKotlin.view.datawrapper.ProgramViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.ui.viewholder.ProgramListViewHolder
import javax.inject.Inject

@PerFragment
class UserProgramsListAdapter @Inject constructor(
    private val context: Context
) : RecyclerView.Adapter<ProgramListViewHolder>() {

    var onProgramSelectedListener: (programId: String) -> Unit = {}

    private val programList = mutableListOf<ProgramViewDataWrapper>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProgramListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_user_programs_list_item, parent, false), context)


    override fun onBindViewHolder(holder: ProgramListViewHolder, position: Int) {
        holder.bind(programList[position], onProgramSelectedListener)
    }

    override fun getItemCount(): Int {
        return programList.size
    }

    fun updateProgramList(programList: List<ProgramViewDataWrapper>) {
        this.programList.clear()
        this.programList.addAll(programList)
        notifyDataSetChanged()
    }
}
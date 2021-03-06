package thomas.guitartrainingkotlin.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.FragmentScoped
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.ui.viewholder.ProgramListViewHolder
import thomas.guitartrainingkotlin.presentation.view.datawrapper.ProgramViewDataWrapper
import javax.inject.Inject

@FragmentScoped
class UserProgramsListAdapter @Inject constructor(
    private val context: Context
) : RecyclerView.Adapter<ProgramListViewHolder>() {

    var onProgramSelectedListener: (programId: String, view: View) -> Unit = { _, _ -> }

    private val programList = mutableListOf<ProgramViewDataWrapper>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProgramListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_user_programs_list_item,
                parent,
                false
            ), context
        )


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
package thomas.example.com.guitarTrainingKotlin.fragment.user

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_user_program_details.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserProgramActivity
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.viewmodel.UserProgramDetailsViewModel
import javax.inject.Inject

class UserProgramDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userProgramDetailsViewModel: UserProgramDetailsViewModel

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    private lateinit var idProgram: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_user_program_details, container, false)

        userProgramDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserProgramDetailsViewModel::class.java)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = requireActivity().intent.extras
        if (bundle != null) {
            if (bundle.containsKey(UserProgramActivity.ID_PROGRAM)) {
                idProgram = bundle.getString(UserProgramActivity.ID_PROGRAM)
                if (idProgram == "1" || idProgram == "2") {
                    fragment_user_program_details_remove_button.visibility = View.GONE
                    fragment_user_program_details_update_button.visibility = View.GONE
                    val constraintSet = ConstraintSet()
                    constraintSet.clone(fragment_user_program_details_constraint_layout)
                    constraintSet.clear(R.id.fragment_user_program_details_start_button, ConstraintSet.TOP)
                    constraintSet.connect(R.id.fragment_user_program_details_start_button, ConstraintSet.BOTTOM, R.id.fragment_user_program_details_constraint_layout, ConstraintSet.BOTTOM, 8)
                    constraintSet.applyTo(fragment_user_program_details_constraint_layout)
                }
            }
        }

        userProgramDetailsViewModel.finishRetrieveProgram.observe(this, Observer<Boolean> {
            if (it == true) {
                val userProgramObjectWrapper = userProgramDetailsViewModel.userProgramObjectWrapper

                fragment_user_program_details_name.text = userProgramObjectWrapper.program.nameProgram
                fragment_user_program_details_description.text = userProgramObjectWrapper.program.descriptionProgram

                if (activity is UserProgramActivity) {
                    (activity as UserProgramActivity).setToolbar(userProgramObjectWrapper.program.nameProgram)
                }
            }
        })

        userProgramDetailsViewModel.finishLoading.observe(this, Observer<Boolean> {
            if (it != null) {
                materialDialogComponent.dismissDialog()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        materialDialogComponent.showProgressDialog(this.activity!!, getString(R.string.dialog_loading_program_details_title), getString(R.string.dialog_loading_content), R.color.colorPrimary)
        userProgramDetailsViewModel.getProgramById(idProgram)
    }

}
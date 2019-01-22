package thomas.example.com.guitarTrainingKotlin.fragment.song

import android.os.Bundle
import android.util.LongSparseArray
import android.view.View
import androidx.navigation.Navigation.findNavController
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_user_song_details.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserSongActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponentImpl
import thomas.example.com.guitarTrainingKotlin.component.listener.MultipleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.component.listener.SingleChoiceMaterialDialogListener
import thomas.example.com.guitarTrainingKotlin.extension.observeSafe
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.chart.ChartMarkerView
import thomas.example.com.guitarTrainingKotlin.ui.chart.HourAxisValueFormatter
import thomas.example.com.guitarTrainingKotlin.ui.viewdatawrapper.SongViewDataWrapper
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserSongDetailsViewModel
import javax.inject.Inject

class UserSongDetailsFragment : BaseFragment<UserSongDetailsViewModel>() {

    override val viewModelClass = UserSongDetailsViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_song_details

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    @Inject
    lateinit var materialDialogComponentImpl: MaterialDialogComponentImpl

    private lateinit var idSong: String

    private var mSelectedItem: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = requireActivity().intent.extras
        if (bundle != null) {
            if (bundle.containsKey(ConstValues.ID_SONG)) {
                idSong = bundle.getString(ConstValues.ID_SONG)
            }
        }

        viewModel.retrieveSongScoreHistoric(idSong)

        handleLiveData(view)
        handleStartSong()
        handleUpdateSong()
        handleRemoveSong()
    }

    override fun onStart() {
        super.onStart()
        materialDialogComponentImpl.showProgressDialog(
            getString(R.string.dialog_details_song_title),
            getString(R.string.dialog_details_song_content),
            R.color.colorPrimary
        )
        viewModel.getSongById(idSong)
    }

    private fun handleLiveData(view: View) {
        viewModel.finishRetrieveSongForDetails.observeSafe(this) {
            if (it == true) {
                val userSongObjectWrapper = viewModel.userSongViewDataWrapper
                displayInformation(userSongObjectWrapper)
                viewModel.finishRetrieveSongForDetails.removeObservers(this)
            }
        }

        viewModel.finishLoading.observeSafe(this) {
            if (it != null) {
                materialDialogComponentImpl.dismissDialog()
            }
        }

        viewModel.finishSongDeletion.observeSafe(this) {
            if (it != null && it == true) {
                activity?.finish()
            }
        }

        viewModel.finishFeedbackSending.observeSafe(this) {
            if (it != null && it == true) {
                activity?.finish()
            } else {
//                errorRendererComponent.requestRenderError(
//                    viewModel.errorThrowable as Throwable,
//                    ErrorRendererComponentImpl.ERROR_DISPLAY_MODE_SNACKBAR,
//                    view
//                )
                activity?.finish()
            }
        }

        viewModel.finishRetrieveSongScoreHistoric.observeSafe(this) {
            if (it != null && it == true) {
                displayHistoricValues(viewModel.timestampKeyList)
            } else {
//                errorRendererComponent.requestRenderError(
//                    viewModel.errorThrowable as Throwable,
//                    ErrorRendererComponentImpl.ERROR_DISPLAY_MODE_SNACKBAR,
//                    view
//                )
            }
        }
    }

    private fun displayInformation(userSongViewDataWrapper: SongViewDataWrapper) {
        val titleSong = userSongViewDataWrapper.getTitleSong()
        val artistSong = userSongViewDataWrapper.getArtistSong()

        setToolbar(titleSong)

        fragment_user_song_details_name.text = titleSong
        fragment_user_song_details_description.text = artistSong
    }

    private fun setToolbar(nameSong: String) {
        if (activity is UserSongActivity) {
            (activity as UserSongActivity).setToolbar(nameSong)
        }
    }

    private fun displayHistoricValues(timestampKeyList: LongSparseArray<Float>) {
        if (timestampKeyList.size() != 0) {
            val dataValues = ArrayList<Entry>()
            for (i in 0 until timestampKeyList.size()) {
                val key = timestampKeyList.keyAt(i)
                dataValues.add(Entry(key.toFloat(), timestampKeyList.get(key)))
            }

            val xAxisFormatter = HourAxisValueFormatter(viewModel.referenceTimestamp)
            val xAxis = fragment_user_song_chart.xAxis
            xAxis.valueFormatter = xAxisFormatter
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            val dataSet = LineDataSet(dataValues, activity?.getString(R.string.label_chart))
            val lineData = LineData(dataSet)
            fragment_user_song_chart.data = lineData

            fragment_user_song_chart.description = null
            fragment_user_song_chart.axisRight.isEnabled = false

            val chartMarkerView =
                ChartMarkerView(context, R.layout.chart_marker_view_layout, viewModel.referenceTimestamp)
            chartMarkerView.chartView = fragment_user_song_chart
            fragment_user_song_chart.marker = chartMarkerView

            fragment_user_song_chart.invalidate()
        }
    }

    private fun handleStartSong() {
        fragment_user_song_details_start_button.setOnClickListener {
            materialDialogComponentImpl.showSingleChoiceDialog(
                getString(R.string.dialog_details_song_score_title),
                resources.getStringArray(R.array.list_scores).toMutableList(),
                mSelectedItem,
                R.color.colorPrimary,
                true,
                object : SingleChoiceMaterialDialogListener {

                    override fun onItemSelected(selectedItem: String) {
                        materialDialogComponentImpl.showProgressDialog(
                            getString(R.string.dialog_send_feedback_title),
                            getString(R.string.dialog_send_feedback_content),
                            R.color.colorPrimary
                        )
                        mSelectedItem = selectedItem
                        viewModel.sendSongFeedback(selectedItem.toInt(), idSong)
                    }

                    override fun onCancelClick() {}

                    override fun getPositionSelected(which: Int) {}
                })
        }
    }

    private fun handleUpdateSong() {
        fragment_user_song_details_update_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable(
                UserSongUpdateFragment.SONG_OBJECT_WRAPPER_KEY,
                viewModel.userSongViewDataWrapper
            )

            val host = activity?.findViewById(R.id.user_song_nav_host_fragment) as View
            findNavController(host).navigate(R.id.user_song_update, bundle, null)
        }
    }

    private fun handleRemoveSong() {
        fragment_user_song_details_remove_button.setOnClickListener {
            materialDialogComponentImpl.showMultiChoiceDialog(
                getString(R.string.dialog_remove_song_title),
                getString(R.string.dialog_remove_song_confirm_content),
                R.color.colorPrimary,
                object : MultipleChoiceMaterialDialogListener {
                    override fun onYesSelected() {
                        materialDialogComponentImpl.showProgressDialog(
                            getString(R.string.dialog_remove_song_title),
                            getString(R.string.dialog_remove_song_content),
                            R.color.colorPrimary
                        )
                        viewModel.removeSong(idSong)
                    }
                })
        }
    }
}

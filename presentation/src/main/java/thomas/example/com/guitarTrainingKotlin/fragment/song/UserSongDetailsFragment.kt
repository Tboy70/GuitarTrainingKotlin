package thomas.example.com.guitarTrainingKotlin.fragment.song

import android.os.Bundle
import android.util.LongSparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_user_song_details.*
import thomas.example.com.guitarTrainingKotlin.R
import thomas.example.com.guitarTrainingKotlin.activity.UserSongActivity
import thomas.example.com.guitarTrainingKotlin.component.ErrorRendererComponent
import thomas.example.com.guitarTrainingKotlin.component.MaterialDialogComponent
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

class UserSongDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userSongDetailsViewModel: UserSongDetailsViewModel

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    private lateinit var idSong: String

    private var mSelectedItem: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_user_song_details, container, false)

        userSongDetailsViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(UserSongDetailsViewModel::class.java)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = requireActivity().intent.extras
        if (bundle != null) {
            if (bundle.containsKey(ConstValues.ID_SONG)) {
                idSong = bundle.getString(ConstValues.ID_SONG)
            }
        }

        userSongDetailsViewModel.retrieveSongScoreHistoric(idSong)

        handleLiveData(view)
        handleStartSong()
        handleUpdateSong()
        handleRemoveSong()
    }

    override fun onStart() {
        super.onStart()
        materialDialogComponent.showProgressDialog(
                getString(R.string.dialog_details_song_title),
                getString(R.string.dialog_details_song_content),
                R.color.colorPrimary
        )
        userSongDetailsViewModel.getSongById(idSong)
    }

    private fun handleLiveData(view: View) {
        userSongDetailsViewModel.finishRetrieveSongForDetails.observeSafe(this) {
            if (it == true) {
                val userSongObjectWrapper = userSongDetailsViewModel.userSongViewDataWrapper
                displayInformation(userSongObjectWrapper)
                userSongDetailsViewModel.finishRetrieveSongForDetails.removeObservers(this)
            }
        }

        userSongDetailsViewModel.finishLoading.observeSafe(this) {
            if (it != null) {
                materialDialogComponent.dismissDialog()
            }
        }

        userSongDetailsViewModel.finishSongDeletion.observeSafe(this) {
            if (it != null && it == true) {
                activity?.finish()
            }
        }

        userSongDetailsViewModel.finishFeedbackSending.observeSafe(this) {
            if (it != null && it == true) {
                activity?.finish()
            } else {
                errorRendererComponent.requestRenderError(
                        userSongDetailsViewModel.errorThrowable as Throwable,
                        ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR,
                        view
                )
                activity?.finish()
            }
        }

        userSongDetailsViewModel.finishRetrieveSongScoreHistoric.observeSafe(this) {
            if (it != null && it == true) {
                displayHistoricValues(userSongDetailsViewModel.timestampKeyList)
            } else {
                errorRendererComponent.requestRenderError(
                        userSongDetailsViewModel.errorThrowable as Throwable,
                        ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR,
                        view
                )
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

            val xAxisFormatter = HourAxisValueFormatter(userSongDetailsViewModel.referenceTimestamp)
            val xAxis = fragment_user_song_chart.xAxis
            xAxis.valueFormatter = xAxisFormatter
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            val dataSet = LineDataSet(dataValues, activity?.getString(R.string.label_chart))
            val lineData = LineData(dataSet)
            fragment_user_song_chart.data = lineData

            fragment_user_song_chart.description = null
            fragment_user_song_chart.axisRight.isEnabled = false

            val chartMarkerView =
                ChartMarkerView(context, R.layout.chart_marker_view_layout, userSongDetailsViewModel.referenceTimestamp)
            chartMarkerView.chartView = fragment_user_song_chart
            fragment_user_song_chart.marker = chartMarkerView

            fragment_user_song_chart.invalidate()
        }
    }

    private fun handleStartSong() {
        fragment_user_song_details_start_button.setOnClickListener {
            materialDialogComponent.showSingleChoiceDialog(
                    getString(R.string.dialog_details_song_score_title),
                    resources.getStringArray(R.array.list_scores).toMutableList(),
                    mSelectedItem,
                    R.color.colorPrimary,
                    true,
                    object : SingleChoiceMaterialDialogListener {

                        override fun onItemSelected(selectedItem: String) {
                            materialDialogComponent.showProgressDialog(
                                    getString(R.string.dialog_send_feedback_title),
                                    getString(R.string.dialog_send_feedback_content),
                                    R.color.colorPrimary
                            )
                            mSelectedItem = selectedItem
                            userSongDetailsViewModel.sendSongFeedback(selectedItem.toInt(), idSong)
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
                    userSongDetailsViewModel.userSongViewDataWrapper
            )

            val host = activity?.findViewById(R.id.user_song_nav_host_fragment) as View
            findNavController(host).navigate(R.id.user_song_update, bundle, null)
        }
    }

    private fun handleRemoveSong() {
        fragment_user_song_details_remove_button.setOnClickListener {
            materialDialogComponent.showMultiChoiceDialog(
                    getString(R.string.dialog_remove_song_title),
                    getString(R.string.dialog_remove_song_confirm_content),
                    R.color.colorPrimary,
                    object : MultipleChoiceMaterialDialogListener {
                        override fun onYesSelected() {
                            materialDialogComponent.showProgressDialog(
                                    getString(R.string.dialog_remove_song_title),
                                    getString(R.string.dialog_remove_song_content),
                                    R.color.colorPrimary
                            )
                            userSongDetailsViewModel.removeSong(idSong)
                        }
                    })
        }
    }
}

package thomas.guitartrainingkotlin.presentation.fragment.song

import android.os.Bundle
import android.util.LongSparseArray
import android.view.MenuItem
import android.view.View
import androidx.navigation.Navigation.findNavController
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.fragment_user_song_details.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.component.DialogComponentImpl
import thomas.guitartrainingkotlin.presentation.component.ErrorRendererComponentImpl
import thomas.guitartrainingkotlin.presentation.extension.*
import thomas.guitartrainingkotlin.presentation.fragment.BaseFragment
import thomas.guitartrainingkotlin.presentation.ui.chart.ChartMarkerView
import thomas.guitartrainingkotlin.presentation.ui.chart.HourAxisValueFormatter
import thomas.guitartrainingkotlin.presentation.utils.ConstValues
import thomas.guitartrainingkotlin.presentation.view.datawrapper.SongViewDataWrapper
import thomas.guitartrainingkotlin.presentation.viewmodel.user.UserSongDetailsViewModel
import javax.inject.Inject

class UserSongDetailsFragment : BaseFragment<UserSongDetailsViewModel>() {

    override val viewModelClass = UserSongDetailsViewModel::class
    override fun getLayoutId(): Int = R.layout.fragment_user_song_details

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponentImpl

    @Inject
    lateinit var dialogComponent: DialogComponentImpl

    private var navHost: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            it.intent.extras?.let { bundle ->
                bundle.getString(ConstValues.ID_SONG)?.let { idSong ->
                    viewModel.setIdSong(idSong)
                }
            }

            navHost = it.findViewById(R.id.user_song_nav_host_fragment) as View
        }

        viewModel.retrieveSongScoreHistory()
        viewModel.getSongById()

        initiateToolbar()
        initiateView()
        initiateViewModelObservers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initiateToolbar() {
        setHasOptionsMenu(true)
        activity?.setSupportActionBar(fragment_user_song_details_toolbar, ActivityExtensions.DISPLAY_UP)
    }

    private fun initiateView() {
        fragment_user_song_details_rate_button.setOnClickListener {
            dialogComponent.displaySingleListChoiceDialog(
                R.string.dialog_details_song_score_title,
                R.array.list_scores,
                android.R.string.ok,
                onPositive = { rate ->
                    viewModel.sendSongFeedback(rate)
                }
            )
        }

        fragment_user_song_details_remove_button.setOnClickListener {
            dialogComponent.displayDualChoiceDialog(
                R.string.dialog_remove_song_title,
                R.string.dialog_remove_song_confirm_content,
                android.R.string.yes,
                android.R.string.cancel,
                onPositive = {
                    viewModel.removeSong()
                },
                onNegative = {}
            )
        }

        fragment_user_song_details_update_button.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(ConstValues.ID_SONG, viewModel.getIdSong())
            navHost?.let { view ->
                findNavController(view).navigate(R.id.user_song_update, bundle, null)
            }
        }
    }

    private fun initiateViewModelObservers() {
        viewModel.songRetrievedLiveData.observeSafe(this) {
            displaySongInformation(it)
        }

        viewModel.songDeletedLiveEvent.observeSafe(this) {
            if (it != null && it == true) {
                activity?.finish()
            }
        }

        viewModel.scoreHistoryRetrieved.observeSafe(this) {
            if (it != null) {
                displayHistoricValues(it)
            }
        }

        viewModel.viewState.observeSafe(this) {
            if (it.loading) {
                fragment_user_song_details_progress_bar.show()
            } else {
                fragment_user_song_details_progress_bar.gone()
            }
        }

        viewModel.errorLiveEvent.observeSafe(this) {
            errorRendererComponent.displayError(it)
        }
    }

    private fun displaySongInformation(userSongViewDataWrapper: SongViewDataWrapper) {
        fragment_user_song_details_name.text = userSongViewDataWrapper.getTitleSong()
        fragment_user_song_details_artist.text = userSongViewDataWrapper.getArtistSong()
    }

    private fun displayHistoricValues(timestampKeyList: LongSparseArray<Float>) {
        if (timestampKeyList.size() != 0) {
            val dataValues = ArrayList<Entry>()
            for (i in 0 until timestampKeyList.size()) {
                val key = timestampKeyList.keyAt(i)
                dataValues.add(Entry(key.toFloat(), timestampKeyList.get(key)))
            }

            val xAxisFormatter =
                HourAxisValueFormatter(viewModel.referenceTimestamp)
            val xAxis = fragment_user_song_chart.xAxis
            xAxis.valueFormatter = xAxisFormatter
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            val dataSet = LineDataSet(dataValues, activity?.getString(R.string.label_chart))
            val lineData = LineData(dataSet)
            fragment_user_song_chart.data = lineData

            fragment_user_song_chart.description = null
            fragment_user_song_chart.axisRight.isEnabled = false

            val chartMarkerView =
                ChartMarkerView(
                    context,
                    R.layout.chart_marker_view_layout,
                    viewModel.referenceTimestamp
                )
            chartMarkerView.chartView = fragment_user_song_chart
            fragment_user_song_chart.marker = chartMarkerView

            fragment_user_song_chart.invalidate()
        }
    }
}
package thomas.example.com.guitarTrainingKotlin.fragment.song

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
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
import thomas.example.com.guitarTrainingKotlin.fragment.BaseFragment
import thomas.example.com.guitarTrainingKotlin.ui.chart.HourAxisValueFormatter
import thomas.example.com.guitarTrainingKotlin.ui.objectwrapper.SongObjectWrapper
import thomas.example.com.guitarTrainingKotlin.utils.ConstValues
import thomas.example.com.guitarTrainingKotlin.utils.DateTimeUtils
import thomas.example.com.guitarTrainingKotlin.viewmodel.user.UserSongDetailsViewModel
import thomas.example.com.model.Score
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap


class UserSongDetailsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userSongDetailsViewModel: UserSongDetailsViewModel

    @Inject
    lateinit var errorRendererComponent: ErrorRendererComponent

    @Inject
    lateinit var materialDialogComponent: MaterialDialogComponent

    private lateinit var idSong: String
    private var referenceTimestamp: Long = 1000000000000

    private var mSelectedItem: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_user_song_details, container, false)

        userSongDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(UserSongDetailsViewModel::class.java)

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
        materialDialogComponent.showProgressDialog(getString(R.string.dialog_details_song_title), getString(R.string.dialog_details_song_content), R.color.colorPrimary)
        userSongDetailsViewModel.getSongById(idSong)
    }

    private fun handleLiveData(view: View) {
        userSongDetailsViewModel.finishRetrieveSongForDetails.observe(this, Observer<Boolean> {
            if (it == true) {
                val userSongObjectWrapper = userSongDetailsViewModel.userSongObjectWrapper
                displayInformation(userSongObjectWrapper)
                userSongDetailsViewModel.finishRetrieveSongForDetails.removeObservers(this)
            }
        })

        userSongDetailsViewModel.finishLoading.observe(this, Observer<Boolean> {
            if (it != null) {
                materialDialogComponent.dismissDialog()
            }
        })

        userSongDetailsViewModel.finishSongDeletion.observe(this, Observer<Boolean> {
            if (it != null && it == true) {
                activity?.finish()
            }
        })

        userSongDetailsViewModel.finishFeedbackSending.observe(this, Observer<Boolean> {
            if (it != null && it == true) {
                activity?.finish()
            } else {
                errorRendererComponent.requestRenderError(userSongDetailsViewModel.errorThrowable as Throwable, ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR, view)
                activity?.finish()
            }
        })

        userSongDetailsViewModel.finishRetrieveSongScoreHistoric.observe(this, Observer<Boolean> {
            if (it != null && it == true) {
                displayHistoricValues(userSongDetailsViewModel.songScoreHistoric)
            } else {
                errorRendererComponent.requestRenderError(userSongDetailsViewModel.errorThrowable as Throwable, ErrorRendererComponent.ERROR_DISPLAY_MODE_SNACKBAR, view)
                //TODO : Handle
            }
        })
    }

    private fun displayInformation(userSongObjectWrapper: SongObjectWrapper) {
        val titleSong = userSongObjectWrapper.song.titleSong
        val artistSong = userSongObjectWrapper.song.artistSong

        setToolbar(titleSong)

        fragment_user_song_details_name.text = titleSong
        fragment_user_song_details_description.text = artistSong
    }

    private fun setToolbar(nameSong: String) {
        if (activity is UserSongActivity) {
            (activity as UserSongActivity).setToolbar(nameSong)
        }
    }

    private fun displayHistoricValues(songScoreHistoric: List<Score>) {

        val entries = HashMap<Long, Float>()
        val dateFormat = SimpleDateFormat(DateTimeUtils.FROM_API_FORMAT, Locale.FRANCE)

        for (score in songScoreHistoric) {
            val timestamp = Timestamp(dateFormat.parse(score.dateScore).time).time / 1000
            if (timestamp < referenceTimestamp) {
                referenceTimestamp = timestamp
            }
            entries[timestamp] = score.valueScore
        }

        val newEntries = HashMap<Long, Float>()
        for (entry in entries) {
            newEntries[entry.key - referenceTimestamp] = entry.value
        }


        entries.clear()

        Log.e("TEST", "OKLM")

        val test = newEntries.toSortedMap()

        val dataValues = ArrayList<Entry>()
        for (entry in test) {
            dataValues.add(Entry(entry.key.toFloat(), entry.value))
        }

        val xAxisFormatter = HourAxisValueFormatter(referenceTimestamp)
        val xAxis = fragment_user_song_chart.xAxis
        xAxis.valueFormatter = xAxisFormatter
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        fragment_user_song_chart.axisRight.isEnabled = false

        val dataSet = LineDataSet(dataValues, "Notes")
        val lineData = LineData(dataSet)
        fragment_user_song_chart.data = lineData
        fragment_user_song_chart.invalidate()
    }

    private fun handleStartSong() {
        fragment_user_song_details_start_button.setOnClickListener {
            materialDialogComponent.showSingleChoiceDialog(getString(R.string.dialog_details_song_score_title), resources.getStringArray(R.array.list_scores).toMutableList(), mSelectedItem, R.color.colorPrimary, true, object : SingleChoiceMaterialDialogListener {

                override fun onItemSelected(selectedItem: String) {
                    materialDialogComponent.showProgressDialog(getString(R.string.dialog_send_feedback_title), getString(R.string.dialog_send_feedback_content), R.color.colorPrimary)
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
            bundle.putSerializable(UserSongUpdateFragment.SONG_OBJECT_WRAPPER_KEY, userSongDetailsViewModel.userSongObjectWrapper)

            val host = activity?.supportFragmentManager?.findFragmentById(R.id.user_song_nav_host_fragment) as NavHostFragment
            NavHostFragment.findNavController(host).navigate(R.id.user_song_update, bundle, null)
        }
    }

    private fun handleRemoveSong() {
        fragment_user_song_details_remove_button.setOnClickListener {
            materialDialogComponent.showMultiChoiceDialog(getString(R.string.dialog_remove_song_title), getString(R.string.dialog_remove_song_confirm_content), R.color.colorPrimary, object : MultipleChoiceMaterialDialogListener {
                override fun onYesSelected() {
                    materialDialogComponent.showProgressDialog(getString(R.string.dialog_remove_song_title), getString(R.string.dialog_remove_song_content), R.color.colorPrimary)
                    userSongDetailsViewModel.removeSong(idSong)
                }
            })
        }
    }
}
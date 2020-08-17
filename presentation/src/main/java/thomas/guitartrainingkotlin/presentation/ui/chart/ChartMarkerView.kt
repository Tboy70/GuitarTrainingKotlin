package thomas.guitartrainingkotlin.presentation.ui.chart

import android.annotation.SuppressLint
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import kotlinx.android.synthetic.main.chart_marker_view_layout.view.*
import thomas.guitartrainingkotlin.R
import thomas.guitartrainingkotlin.presentation.utils.DateTimeUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ViewConstructor")
class ChartMarkerView constructor(
    context: Context,
    layoutResource: Int,
    private val referenceTimestamp: Long
) :
    MarkerView(context, layoutResource) {

    private val mDataFormat: DateFormat
    private val mDate: Date

    init {
        this.mDataFormat = SimpleDateFormat(DateTimeUtils.WANTED_FORMAT, Locale.FRENCH)
        this.mDate = Date()
    }

    override fun refreshContent(e: Entry, highlight: Highlight) {
        val currentTimestamp = e.x.toInt() + referenceTimestamp

        chart_marker_view_content.text = String.format(
            context.getString(R.string.chart_marker_content),
            "%.2f".format(e.y),
            getTimeDate(currentTimestamp)
        )
    }

    fun getXOffset(xpos: Float): Int {
        // this will center the marker-view horizontally
        val minOffset = 50
        if (xpos < minOffset)
            return 0

        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(metrics)
        //For right hand side
        if (metrics.widthPixels - xpos < minOffset)
            return -width
        else if (metrics.widthPixels - xpos < 0)
            return -width//For left hand side
        return -(width / 2)
    }

    fun getYOffset(ypos: Float): Int {
        // this will cause the marker-view to be above the selected value
        return -height
    }

    private fun getTimeDate(timestamp: Long): String {
        return try {
            mDate.time = timestamp * 1000
            mDataFormat.format(mDate)
        } catch (ex: Exception) {
            "xx"
        }
    }
}

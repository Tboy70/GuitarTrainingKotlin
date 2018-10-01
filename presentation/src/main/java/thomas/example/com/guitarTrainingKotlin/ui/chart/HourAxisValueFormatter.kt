package thomas.example.com.guitarTrainingKotlin.ui.chart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import thomas.example.com.guitarTrainingKotlin.utils.DateTimeUtils
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HourAxisValueFormatter(private val referenceTimestamp: Long) : IAxisValueFormatter {

    private val mDataFormat: DateFormat
    private val mDate: Date

    init {
        this.mDataFormat = SimpleDateFormat(DateTimeUtils.WANTED_FORMAT_WITHOUT_YEAR, Locale.FRANCE)
        this.mDate = Date()
    }

    /**
     * Called when a value from an axis is to be formatted
     * before being drawn. For performance reasons, avoid excessive calculations
     * and memory allocations inside this method.
     *
     * @param value the value to be formatted
     * @param axis  the axis the value belongs to
     * @return
     */
    override fun getFormattedValue(value: Float, axis: AxisBase): String {
        // convertedTimestamp = originalTimestamp - referenceTimestamp
        val convertedTimestamp = value.toLong()

        // Retrieve original timestamp
        val originalTimestamp = referenceTimestamp + convertedTimestamp

        // Convert timestamp to hour:minute
        return getHour(originalTimestamp)
    }

    private fun getHour(timestamp: Long): String {
        return try {
            mDate.time = timestamp * 1000
            mDataFormat.format(mDate)
        } catch (ex: Exception) {
            "xx"
        }
    }
}
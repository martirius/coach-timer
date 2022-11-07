package pini.mattia.coachtimer.ui.customcomposables

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@Composable
fun Graph(modifier: Modifier, values: List<Long>, averageValue: Long) {
    Surface {
        Box(
            modifier = modifier
                .height(200.dp)
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 8.dp, vertical = 12.dp)
        ) {
            if (values.isNotEmpty()) {
                val maxValue = values.maxOrNull() ?: 0L
                val closestNumber = findClosestNumber(maxValue, 1000L)
                val numberOfYAxisElement = minOf(7, values.size)
                val yStep = closestNumber / numberOfYAxisElement
                val yAxisValue = LongArray(numberOfYAxisElement) { index -> yStep * (index + 1) }
                Canvas(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(max(48.dp * 2, 48.dp * values.size)) // make it greater than row
                ) {
                    val textSize = 14.sp.toPx()
                    val textPaint = Paint().apply {
                        color = Color.BLACK
                        style = Paint.Style.FILL
                        this.textSize = textSize
                    }

                    val cartesianHeight = size.height - textSize
                    val xAxisSpace = 48.dp.toPx()
                    val yAxisSpace = cartesianHeight / yAxisValue.size

                    // draw x axis line
                    drawLine(
                        color = androidx.compose.ui.graphics.Color.Black,
                        start = Offset(0f, cartesianHeight),
                        end = Offset(xAxisSpace * values.size, cartesianHeight),
                        strokeWidth = 1.dp.toPx()
                    )

                    // draw y axis line
                    drawLine(
                        color = androidx.compose.ui.graphics.Color.Black,
                        start = Offset(xAxisSpace, 0f),
                        end = Offset(xAxisSpace, size.height),
                        strokeWidth = 1.dp.toPx()
                    )

                    // draw average lap time line
                    drawLine(
                        color = androidx.compose.ui.graphics.Color.Black,
                        start = Offset(
                            xAxisSpace,
                            size.height - size.height * (averageValue.toFloat() / maxValue)
                        ),
                        end = Offset(
                            xAxisSpace * values.size,
                            size.height - size.height * (averageValue.toFloat() / maxValue)
                        ),
                        strokeWidth = 1.dp.toPx()
                    )

                    /** placing x axis points */

                    /** placing x axis points */
                    for (i in values.indices) {
                        drawContext.canvas.nativeCanvas.drawText(
                            "${i + 1}",
                            xAxisSpace * (i + 1),
                            size.height,
                            textPaint
                        )
                    }

                    /** placing y axis points */

                    /** placing y axis points */
                    for (i in yAxisValue.indices) {
                        drawContext.canvas.nativeCanvas.drawText(
                            "${yAxisValue[i]}",
                            0f,
                            cartesianHeight - yAxisSpace * (i + 1),
                            textPaint
                        )
                    }

                    /** placing points */
                    /** placing points */
                    val points = List(values.size) { index ->
                        val x1 = xAxisSpace * (index + 1)
                        val y1 = cartesianHeight - cartesianHeight * (values[index].toFloat() / maxValue)
                        Offset(x1, y1)
                    }

                    // draw lines connecting points
                    for (i in 0 until (points.size - 1)) {
                        drawLine(
                            color = androidx.compose.ui.graphics.Color.Black,
                            start = points[i],
                            end = points[i + 1],
                            strokeWidth = 1.dp.toPx()
                        )
                    }

                    // draw points
                    drawPoints(points, PointMode.Points, androidx.compose.ui.graphics.Color.Red, strokeWidth = 4.dp.toPx(), alpha = 1f)
                }
            }
        }
    }
}

private fun findClosestNumber(number: Long, divider: Long): Long {
    // find the quotient
    val q: Long = number / divider

    // 1st possible closest number
    val n1: Long = divider * q

    // 2nd possible closest number
    val n2: Long = if (number * divider > 0) divider * (q + 1) else divider * (q - 1)

    // if true, then n1 is the required closest number
    return if (abs(number - n1) < abs(number - n2)) n1 else n2
}

@Composable
@Preview
fun GraphPreview() {
    Graph(
        modifier = Modifier,
        values = listOf(
            5893L,
            18904L,
            8888L,
            5893L,
            18904L,
            8888L,
            5893L,
            18904L,
            8888L,
            5893L,
            18904L,
            8888L
        ),
        7777L
    )
}

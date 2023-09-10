package com.example.moneytracker.charts

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun DonutPieChart(
    data: List<DonutChartInput>,
    modifier: Modifier = Modifier,
    strokeWidth: Float = 20f
) {
    val totalSum = data.sumOf { it.value }

    Canvas(
        modifier = modifier,
        onDraw = {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2 - strokeWidth / 2

            var startAngle = -90f // Starting from the top
            var index = 0

            data.forEach { donutChartInput ->
                val sweepAngle = 360 * (donutChartInput.value / totalSum)
                val separationAngle = 4f

                drawArc(
                    color = if (donutChartInput.value != 0.0) donutChartInput.color
                            else Color.Transparent,
                    startAngle = startAngle - separationAngle / 2,
                    sweepAngle = sweepAngle.toFloat() - separationAngle / 2,
                    topLeft = center - Offset(radius, radius),
                    size = Size(radius * 2, radius * 2),
                    useCenter = false,
                    style = Stroke(width = strokeWidth)
                )

                startAngle += sweepAngle.toFloat()
                index++
            }
        }
    )
}




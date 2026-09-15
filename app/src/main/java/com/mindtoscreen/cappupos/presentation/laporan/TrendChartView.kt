package com.mindtoscreen.cappupos.presentation.laporan

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.mindtoscreen.cappupos.domain.model.DailyTrend

/**
 * Grafik tren penjualan harian sederhana (line chart), tanpa dependency chart library.
 * ponytail: no axis label / tooltip interaktif — cukup untuk kebutuhan overview FR-09.
 */
class TrendChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var data: List<DailyTrend> = emptyList()

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#0A66B2")
        strokeWidth = 4f
        style = Paint.Style.STROKE
    }
    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#1A0A66B2")
        style = Paint.Style.FILL
    }
    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#0A66B2")
        style = Paint.Style.FILL
    }
    private val emptyPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#64748B")
        textSize = 32f
        textAlign = Paint.Align.CENTER
    }

    fun setData(trend: List<DailyTrend>) {
        data = trend
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        val padding = 24f

        if (data.isEmpty() || data.all { it.totalPenjualan == 0.0 }) {
            canvas.drawText("Belum ada data penjualan", w / 2, h / 2, emptyPaint)
            return
        }

        val maxVal = data.maxOf { it.totalPenjualan }.coerceAtLeast(1.0)
        val stepX = if (data.size > 1) (w - 2 * padding) / (data.size - 1) else 0f

        fun pointX(i: Int) = padding + stepX * i
        fun pointY(v: Double) = h - padding - ((v / maxVal) * (h - 2 * padding)).toFloat()

        val linePath = android.graphics.Path()
        val fillPath = android.graphics.Path()
        data.forEachIndexed { i, d ->
            val x = pointX(i)
            val y = pointY(d.totalPenjualan)
            if (i == 0) {
                linePath.moveTo(x, y)
                fillPath.moveTo(x, h - padding)
                fillPath.lineTo(x, y)
            } else {
                linePath.lineTo(x, y)
                fillPath.lineTo(x, y)
            }
        }
        fillPath.lineTo(pointX(data.size - 1), h - padding)
        fillPath.close()

        canvas.drawPath(fillPath, fillPaint)
        canvas.drawPath(linePath, linePaint)
        data.forEachIndexed { i, d ->
            canvas.drawCircle(pointX(i), pointY(d.totalPenjualan), 6f, dotPaint)
        }
    }
}

package com.example.createcolor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class HeartView (context: Context, attrs: AttributeSet) : View(context, attrs)
{
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.RED
    }

    var color: Int = Color.RED
    set(value) {
        field = value
        paint.color = value
        invalidate()
    }

    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()
        val path = Path().apply {
            moveTo(width / 2, height / 5)
            cubicTo(width * 5 / 14, height / 14,
                width / 28, height * 2 / 5,
                width / 2, height * 2 / 3)
            cubicTo(width * 27 / 28, height * 2 / 5,
                width * 12 / 14, height / 14,
                width / 2, height / 5)
        }

        if (canvas != null) { canvas.drawPath(path, paint) }
    }
}
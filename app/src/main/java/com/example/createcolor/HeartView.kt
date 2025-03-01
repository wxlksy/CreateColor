package com.example.createcolor

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class HeartView(context: Context, attrs: AttributeSet) : View(context, attrs)
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

    override fun onDraw(canvas: Canvas)
    {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()

        val scaleFactor = 0.8f
        val scaledWidth = width * scaleFactor
        val scaledHeight = height * scaleFactor

        val path = Path().apply {
            moveTo(scaledWidth / 2, scaledHeight * 0.3f)
            cubicTo(scaledWidth * 0.4f, scaledHeight * 0.1f,
                scaledWidth * 0.1f, scaledHeight * 0.3f,
                scaledWidth / 2, scaledHeight * 0.5f)
            cubicTo(scaledWidth * 0.9f, scaledHeight * 0.3f,
                scaledWidth * 0.6f, scaledHeight * 0.1f,
                scaledWidth / 2, scaledHeight * 0.3f)
        }
        canvas.drawPath(path, paint)
    }
}

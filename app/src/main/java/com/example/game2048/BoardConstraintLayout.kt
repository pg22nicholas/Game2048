package com.example.game2048

import android.content.Context
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*

class BoardConstraintLayout : ConstraintLayout {

    private var cellSize = measuredWidth / 4
    constructor(context : Context) : super(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measuredWidth
        setMeasuredDimension(width, width);
    }

    fun GetPointOnScreen(gridPosition : Vector2) : Vector2Float {
        val x : Float = gridPosition.x * (cellSize.toFloat() / 2) + left
        val y : Float = gridPosition.y * (cellSize.toFloat() / 2) - top;

        return Vector2Float(x, y)
    }
}
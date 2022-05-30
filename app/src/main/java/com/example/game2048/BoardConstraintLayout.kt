package com.example.game2048

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.game2048.models.Vector2

class BoardConstraintLayout : ConstraintLayout {

    constructor(context : Context) : super(context)
    constructor(context : Context, attrs: AttributeSet) : super (context, attrs)

    private var cellSize = measuredWidth / 4

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measuredWidth
        setMeasuredDimension(width, width);
    }

    fun GetPointOnScreen(gridPosition : Vector2) : Vector2 {
        val x : Int = (gridPosition.x * measuredWidth/4) + left
        val y : Int = (gridPosition.y * measuredWidth/4) + top

        return Vector2(x, y)
    }
}
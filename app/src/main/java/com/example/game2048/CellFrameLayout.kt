package com.example.game2048

import android.content.Context
import android.widget.FrameLayout

class CellFrameLayout : FrameLayout {

    constructor(context : Context) : super(context)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measuredWidth
        setMeasuredDimension(width, width);
    }
}
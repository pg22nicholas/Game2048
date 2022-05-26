package com.example.game2048

import android.view.View
import androidx.annotation.Nullable
import java.util.*

class Cell(var value : Int, var currGridPos : Vector2, var nextGridPos : Vector2 = Vector2(currGridPos.x, currGridPos.y))
{
    var isCombined = false
    lateinit var view : View

    init {
        // todo: Initiate the view
    }

    fun attachView(view: View) {
        // TODO: Attach view to this cell, created inside a view class
    }

    fun endRound() {
        isCombined = false
        currGridPos = nextGridPos
    }

    fun destroyCell() {
        // TODO: Destroy view
    }

}

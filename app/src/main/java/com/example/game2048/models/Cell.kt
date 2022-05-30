package com.example.game2048.models

import android.view.View

class Cell(var value : Int, var currGridPos : Vector2, var nextGridPos : Vector2 = Vector2(currGridPos.x, currGridPos.y))
{
    var isCombined = false
    lateinit var view : View

    init {
        // todo: Initiate the view
    }

    fun attachView(view: View) {
        this.view = view
    }

    fun endRound() {
        isCombined = false
        currGridPos = nextGridPos
    }

    fun destroyCell() {
        // TODO: Destroy view
    }

}

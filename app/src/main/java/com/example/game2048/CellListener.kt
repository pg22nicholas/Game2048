package com.example.game2048

import android.view.View

interface CellListener {

    fun createNewCell(pos : Vector2) : View

    fun updateCell(cellView : View, value : Int)

    fun deleteView(cellView : View)
}
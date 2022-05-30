package com.example.game2048

import android.view.View
import com.example.game2048.models.Vector2

interface CellListener {

    fun createNewCell(pos : Vector2) : View

    fun updateCell(cellView : View, value : Int)

    fun deleteView(cellView : View)

    fun addScore(scoreToAdd : Int)

    fun wonGame()
}
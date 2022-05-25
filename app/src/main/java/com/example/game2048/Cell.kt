package com.example.game2048

import androidx.annotation.Nullable
import java.util.*

class Cell(val value : Int, var currGridPos : Vector2, var nextGridPos : Vector2 = Vector2(currGridPos.x, currGridPos.y), var deleteFlag : Boolean = false)
{
    init {
        // todo: Initiate the view
    }
}

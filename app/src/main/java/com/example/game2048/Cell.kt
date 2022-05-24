package com.example.game2048

import java.util.*


data class Cell(val value : Int, var currPosition : Vector2, var nextPosition : Vector2 = Vector2(currPosition.x, currPosition.y), var deleteFlag : Boolean = false)

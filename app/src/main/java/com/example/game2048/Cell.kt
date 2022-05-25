package com.example.game2048

import java.util.*


data class Cell(val value : Int, var currGridPos : Int, var nextGridPos : Int = currGridPos, var deleteFlag : Boolean = false)

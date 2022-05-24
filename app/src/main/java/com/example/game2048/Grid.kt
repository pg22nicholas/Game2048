package com.example.game2048

class Grid() {

    val gridSize : Int = 4
    var gridList = arrayListOf<Cell>()

    init {
        // TODO: Initialize cells inside grid
    }

    // register user swiping in direction and update cell positions in grid
    fun swipe(direction : Direction) {
        // TODO: Go through each cell and calculate their next positions using their position and the nextPosition of the cell they're colliding with
        //  Set cell boolean to delete
    }

    // Notifies grid that the animations were all finished and the cell's current positions should be updated
    fun setPositionsUpdated() {
        // TODO: Set currPosition as nextPosition
    }


    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
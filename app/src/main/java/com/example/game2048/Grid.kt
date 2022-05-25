package com.example.game2048

class Grid() {

    val gridSize : Int = 4
    var gridList = List(4) { mutableListOf<Cell?>()}

    init {
        // TODO: Initialize cells inside grid
        for (i in 0..3) {
            for (j in 0..3) {
                gridList[i][j] = null
            }
        }

        gridList[0][0] = Cell(2, Vector2(0, 0))
        gridList[1][0] = Cell(2, Vector2(1, 0))
    }

    // register user swiping in direction and update cell positions in grid
    fun swipe(direction : Direction) {
        when(direction) {
            Direction.DOWN -> {
                for (i in 3 downTo 0) {
                    for (j in 0..3) {
                        findNewPosition(Vector2(j, i), Vector2(0, -1))
                    }
                }
            }
            Direction.UP -> {
                for (i in 0..3) {
                    for (j in 0..3) {
                        findNewPosition(Vector2(j, i), Vector2(0, 1))
                    }
                }
            }
            Direction.RIGHT -> {
                for (i in 3 downTo 0) {
                    for (j in 0..3) {
                        findNewPosition(Vector2(i, j), Vector2(1, 0))
                    }
                }
            }
            Direction.LEFT -> {
                for (i in 0..3) {
                    for (j in 0..3) {
                        findNewPosition(Vector2(i, j), Vector2(-1, 0))
                    }
                }
            }
        }
    }

    fun findNewPosition(pos : Vector2, direction: Vector2) {
        if (gridList[pos.x][pos.y] == null) return

        val nextPos : Vector2 = Vector2(pos.x + direction.x, pos.y + direction.y)

        // bottom of grid
        if (pos.y == 3) return
        // If next position if null
        if (gridList[nextPos.x][nextPos.y] == null) {
            gridList[nextPos.x][nextPos.y] = gridList[pos.x][pos.y]
            gridList[pos.x][pos.y] = null
            findNewPosition(Vector2(nextPos.x, nextPos.y), direction)
            // if next position same value, combine
        } else if (gridList[pos.x][pos.y]?.value == gridList[nextPos.x][nextPos.y]?.value) {
            val cellToDelete = gridList[nextPos.x][nextPos.y]
            cellToDelete?.deleteFlag = true
            gridList[nextPos.x][nextPos.y] = gridList[pos.x][pos.y]
            gridList[nextPos.x][nextPos.y]?.nextGridPos = Vector2(nextPos.x, nextPos.y)
        // otherwise, next position different value, do nothing
        } else {
            return
        }
    }

    // Notifies grid that the animations were all finished and the cell's current positions should be updated
    fun setPositionsUpdated() {
        // TODO: Set currPosition as nextPosition
    }


    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
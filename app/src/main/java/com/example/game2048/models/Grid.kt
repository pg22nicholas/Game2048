package com.example.game2048.models

import android.util.Log
import com.example.game2048.CellListener
import kotlin.math.abs
import kotlin.random.Random

class Grid(private val cellListener: CellListener) {

    val gridSize : Int = 4

    // gridList[x][y]: holds all numbers on the grid
    var gridList = List(gridSize) { mutableListOf<Cell?>()}

    var cellsToDelete = mutableListOf<Cell>()

    init {
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                gridList[i].add(null)
            }
        }

        spawnRandom()
        testPrint()
    }

    // register user swiping in direction and update cell positions in grid
    fun swipe(direction : Direction) {
        when(direction) {
            Direction.DOWN -> {
                for (x in 0 until gridSize) {
                    for (y in gridSize-1 downTo 0) {
                        findNewPosition(Vector2(x, y), Vector2(0, 1))
                    }
                }
            }
            Direction.UP -> {
                for (x in 0 until gridSize) {
                    for (y in 0 until gridSize) {
                        findNewPosition(Vector2(x, y), Vector2(0, -1))
                    }
                }
            }
            Direction.RIGHT -> {
                for (y in 0 until gridSize) {
                    for (x in gridSize-1 downTo 0) {
                        findNewPosition(Vector2(x, y), Vector2(1, 0))
                    }
                }
            }
            Direction.LEFT -> {
                for (y in 0 until gridSize) {
                    for (x in 0 until gridSize) {
                        findNewPosition(Vector2(x, y), Vector2(-1, 0))
                    }
                }
            }
        }
    }

    // Recursively find the proper solution of the cell to move to, combining with other cells if same value
    private fun findNewPosition(pos : Vector2, direction: Vector2) {
        // No cell to move here
        if (gridList[pos.y][pos.x] == null) return

        val nextX = pos.x + direction.x
        val nextY = pos.y + direction.y

        // Check if next is outside range, then pos at edge of grid
        if (nextY < 0 || nextY > gridSize - 1 || nextX < 0 || nextX > gridSize - 1) return
        val nextPos = Vector2(nextX, nextY)

        // If next position is null
        if (gridList[nextPos.y][nextPos.x] == null) {
            gridList[nextPos.y][nextPos.x] = gridList[pos.y][pos.x]
            gridList[nextPos.y][nextPos.x]?.nextGridPos = Vector2(nextPos.x, nextPos.y)
            gridList[pos.y][pos.x] = null
            findNewPosition(Vector2(nextPos.x, nextPos.y), direction)

        // if next position same value, combine
        } else if (gridList[pos.y][pos.x]?.value == gridList[nextPos.y][nextPos.x]?.value) {

            // don't combine if next position already combined
            if (gridList[nextPos.y][nextPos.x]?.isCombined == true) return

            val cellToDelete = gridList[nextPos.y][nextPos.x]
            // move cell to new position and set cell in new position to delete
            if (cellToDelete != null) {
                cellsToDelete.add(cellToDelete)
            }

            val cellToMove = gridList[pos.y][pos.x]
            gridList[nextPos.y][nextPos.x] = cellToMove
            gridList[pos.y][pos.x] = null

            cellToMove?.value = cellToMove?.value?.times(2)!!
            // if combined value is 2038, player wins
            if (cellToMove.value == 2048)
                cellListener.wonGame()

            cellListener.addScore(cellToMove.value)

            // set next values for view to animate cell's new position
            cellToMove.nextGridPos = Vector2(nextPos.x, nextPos.y)
            cellToMove.isCombined = true
            return;

        // otherwise, next position different value, do nothing
        } else {
            return
        }
    }

    fun testPrint() {
        var printString = " \n"
        for (i in 0 until gridSize) {
            var row = ""
            for (j in 0 until gridSize) {
                row += gridList[i][j]?.value.toString() + " "
            }
            printString += row + "\n"
        }
        Log.d("test", printString)
    }

    // Notifies grid that the animations were all finished and the cell's current positions should be updated
    fun endRound() {
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                var cell = gridList[i][j]
                if (cell != null) {
                    cell.endRound()
                    cellListener.updateCell(cell.view, cell.value)
                }
            }
        }

        for (cell in cellsToDelete) {
            cellListener.deleteView(cell.view)
        }
        cellsToDelete.clear()

        spawnRandom()
    }

    // Checks if it's a game over
    fun checkIsGameOver() : Boolean {
        for (i in 0 until gridSize) {
            for (j in 0 until gridSize) {
                // game not over if there's a valid move still possible
                if (isValidAdjacentMove(Vector2(i, j)))
                    return false
            }
        }
        // no valid moves, game is over
        return true
    }

    // returns true if there is an adjacent tile of the same value
    private fun isValidAdjacentMove(pos : Vector2) :Boolean {
        // if any cell is empty, then there must be a valid move
        if (gridList[pos.y][pos.x] == null) return true

        // if an adjacent cell has the same value, then valid move
        if (pos.x - 1 > 0 && gridList[pos.y][pos.x - 1] != null && gridList[pos.y][pos.x - 1]?.value == gridList[pos.y][pos.x]?.value ||
            pos.x + 1 < gridSize && gridList[pos.y][pos.x + 1] != null && gridList[pos.y][pos.x + 1]?.value == gridList[pos.y][pos.x]?.value ||
            pos.y - 1 > 0 && gridList[pos.y - 1][pos.x] != null && gridList[pos.y - 1][pos.x]?.value == gridList[pos.y][pos.x]?.value ||
            pos.y + 1 < gridSize && gridList[pos.y + 1][pos.x] != null && gridList[pos.y + 1][pos.x]?.value == gridList[pos.y][pos.x]?.value) {
            return true
        }

        return false
    }

    // Create a new cell and spawn at random empty location on the board
    private fun spawnRandom() {
        var randX = abs(Random.nextInt() % gridSize)
        var randY = abs(Random.nextInt() % gridSize)
        while (gridList[randY][randX] != null) {
            randX++
            // goto next row
            if (randX == gridSize) {
                randX = 0
                randY++
                // if hit last element, wrap back to beginning
                if (randY == gridSize) {
                    randX = 0
                    randY = 0
                }
            }
        }

        // Spawn the new random cell
        val randVal : Int = if (Random.nextInt() > 0) 2 else 4
        val cell = Cell(randVal, Vector2(randX, randY))
        val v = cellListener.createNewCell(Vector2(randX, randY))
        cell.attachView(v)
        gridList[randY][randX] = cell
        cellListener.updateCell(v, cell.value)
    }

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
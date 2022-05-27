package com.example.game2048

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GestureDetectorCompat
import java.lang.Math.abs
import kotlin.random.Random


class MainActivity : AppCompatActivity(), CellListener
{
    var gestureDetectorCompat: GestureDetectorCompat? = null
    var isSwipedThisRound = false
    lateinit var grid : Grid
    lateinit var layout : BoardConstraintLayout;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layout = findViewById(R.id.board_container)
        val vto = layout.viewTreeObserver

        var isBoardDrawn = false;

        // initialize grid once board is drawn
        vto.addOnGlobalLayoutListener {
            if (!isBoardDrawn)
                grid = Grid(this)
            isBoardDrawn = true
        }

        gestureDetectorCompat = GestureDetectorCompat(this, object : SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {

                // swipe angle
                val angle =
                    Math.toDegrees(
                        kotlin.math.atan2(
                            (e1.y - e2.y).toDouble(),
                            (e2.x - e1.x).toDouble())
                    ).toFloat()

                // Left swipe
                if (angle > -45 && angle <= 45) {
                    return gestureSwipe(Grid.Direction.RIGHT)
                }
                // Right swipe
                if (angle >= 135 && angle < 180 || angle < -135 && angle > -180) {
                    return gestureSwipe(Grid.Direction.LEFT)
                }
                // Up swipe
                if (angle < -45 && angle >= -135) {
                    return gestureSwipe(Grid.Direction.DOWN)
                }
                // Down swipe
                if (angle > 45 && angle <= 135) {
                    return gestureSwipe(Grid.Direction.UP)
                }
                return false
            }
        })
    }

    private fun gestureSwipe(direction : Grid.Direction) : Boolean {
        // if swipe already registered, wait for animation to complete before listening again
        if (isSwipedThisRound)
            return false
        isSwipedThisRound = true

        // TODO: Only for testing to simulate animation delay
        Handler(Looper.getMainLooper()).postDelayed({
            isSwipedThisRound = false

            // TODO: only for testing
            val rand = kotlin.math.abs(Random.nextInt() % 4)
            if (rand == 0) gestureSwipe(Grid.Direction.DOWN)
            else if (rand == 1) gestureSwipe(Grid.Direction.UP)
            else if (rand == 2) gestureSwipe(Grid.Direction.LEFT)
            else gestureSwipe(Grid.Direction.RIGHT)
        }, 100)

        grid.swipe(direction)
        playerSwipe()
        grid.checkIsGameOver()
        return true
    }

    fun playerSwipe() {
        // TODO: Animate changes
        grid.testPrint()

        var isCellMoved = false
        var gridList = grid.gridList;
        for (i in 0 until grid.gridSize) {
            for (j in 0 until grid.gridSize) {
                if (gridList[i][j] != null)
                {
                    val curr = gridList[i][j]?.currGridPos
                    val next = gridList[i][j]?.nextGridPos
                    // if they are different position, animate to new position

                    if (curr?.x == next?.x && curr?.y == next?.y) continue
                    isCellMoved = true

                    var posNext = next?.let { layout.GetPointOnScreen(it) }
                    var v = gridList[i][j]?.view
                    if (posNext != null && v != null) {
                        v.animate()
                            .translationX(posNext.x.toFloat())
                            .translationY(posNext.y.toFloat())
                            .setDuration(100)
                            .start()
                    }
                }
            }
        }

        // Only register the round ended if a valid move was performed
        if (isCellMoved) {
            grid.endRound()
        }
    }

    fun animateCellChanges() {
        // TODO: Animate all the changes to the cells after a swipe occurred
        //  remove any overlapping Cells by checking their delete flag
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetectorCompat?.onTouchEvent(event)
        return true
    }

    override fun createNewCell(pos: Vector2) : View {
        var LI : LayoutInflater = LayoutInflater.from(applicationContext)
        var v: View = LI.inflate(R.layout.cell, null)
        var pixelPos = layout.GetPointOnScreen(pos)

        v.alpha = 0F
        layout.addView(v)
        val layoutParams = v.layoutParams as ConstraintLayout.LayoutParams
        v.translationX = pixelPos.x.toFloat()
        v.translationY = pixelPos.y.toFloat()

        layoutParams.width = layout.width / 4
        layoutParams.height = layout.width / 4

        v.layoutParams = layoutParams

        v.animate().alpha(1F).setDuration(1000).start()
        return v
    }

    override fun updateCell(cellView: View, value: Int) {
        val textValue = cellView.findViewById<TextView>(R.id.cell_value)
        textValue.text = value.toString()
    }

    override fun deleteView(cellView: View) {
        layout.removeView(cellView)
    }
}
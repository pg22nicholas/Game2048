package com.example.game2048

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import java.lang.Math.atan2


class MainActivity : AppCompatActivity()
{
    var gestureDetectorCompat: GestureDetectorCompat? = null
    var isSwipedThisRound = false
    val grid : Grid = Grid()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val layout = findViewById<BoardConstraintLayout>(R.id.board_container)
        for (i in 0..3) {
            for (j in 0..3) {
                layout.GetPointOnScreen(Vector2(i, j))
            }
        }


        gestureDetectorCompat = GestureDetectorCompat(this, object : SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                // if swipe already registered, wait for animation to complete before listening again
                if (isSwipedThisRound)
                    return false
                isSwipedThisRound = true

                // TODO: Only for testing to simulate animation delay
                Handler(Looper.getMainLooper()).postDelayed({
                    isSwipedThisRound = false
                }, 1000)

                val angle =
                    Math.toDegrees(
                        kotlin.math.atan2(
                            (e1.y - e2.y).toDouble(),
                            (e2.x - e1.x).toDouble()
                        )
                    ).toFloat()
                // Left swipe
                if (angle > -45 && angle <= 45) {
                    Log.d("test", "Right to Left swipe performed")
                    grid.swipe(Grid.Direction.RIGHT)
                    playerSwipe()
                    return true
                }
                // Right swipe
                if (angle >= 135 && angle < 180 || angle < -135 && angle > -180) {
                    Log.d("test", "Left to Right swipe performed")
                    grid.swipe(Grid.Direction.LEFT)
                    playerSwipe()
                    return true
                }
                // Up swipe
                if (angle < -45 && angle >= -135) {
                    Log.d("test", "Up to Down swipe performed")
                    grid.swipe(Grid.Direction.DOWN)
                    playerSwipe()
                    return true
                }
                // Down swipe
                if (angle > 45 && angle <= 135) {
                    Log.d("test", "Down to Up swipe performed")
                    grid.swipe(Grid.Direction.UP)
                    playerSwipe()
                    return true
                }
                return false
            }
        })
    }

    fun playerSwipe() {
        // TODO: Animate changes
        grid.testPrint()
        grid.endRound()
    }

    fun animateCellChanges() {
        // TODO: Animate all the changes to the cells after a swipe occurred
        //  remove any overlapping Cells by checking their delete flag
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetectorCompat?.onTouchEvent(event)
        return true
    }
}
package com.example.game2048

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat


class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureDetectorCompat = GestureDetectorCompat(this, object : SimpleOnGestureListener() {
            override fun onScroll(
                e1: MotionEvent,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                val angle =
                    Math.toDegrees(Math.atan2((e1.y - e2.y).toDouble(), (e2.x - e1.x).toDouble()))
                        .toFloat()
                if (angle > -45 && angle <= 45) {
                    Log.d("test", "Right to Left swipe performed")
                    return true
                }
                if (angle >= 135 && angle < 180 || angle < -135 && angle > -180) {
                    Log.d("test", "Left to Right swipe performed")
                    return true
                }
                if (angle < -45 && angle >= -135) {
                    Log.d("test", "Up to Down swipe performed")
                    return true
                }
                if (angle > 45 && angle <= 135) {
                    Log.d("test", "Down to Up swipe performed")
                    return true
                }
                return false
            }
        })
    }

    fun animateCellChanges() {
        // TODO: Animate all the changes to the cells after a swipe occurred
        //  remove any overlapping Cells by checking their delete flag
    }


    var gestureDetectorCompat: GestureDetectorCompat? = null
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetectorCompat?.onTouchEvent(event)
        return true
    }
}
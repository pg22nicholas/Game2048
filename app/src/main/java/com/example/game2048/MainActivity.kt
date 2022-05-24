package com.example.game2048

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun animateCellChanges() {
        // TODO: Animate all the changes to the cells after a swipe occurred
        //  remove any overlapping Cells by checking their delete flag
    }
}
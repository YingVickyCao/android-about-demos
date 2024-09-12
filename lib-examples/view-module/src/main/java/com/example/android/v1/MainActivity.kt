package com.example.android.v1

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.android.R

/***
 * Click button, refresh the count on the screen
 */
class MainActivity : AppCompatActivity() {
    private lateinit var countView: TextView

    var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countView = findViewById(R.id.count)

        findViewById<View>(R.id.btn).setOnClickListener { clickBtn() }
        displayCount(count)
    }

    private fun clickBtn() {
        plusCount()
    }

    private fun plusCount() {
        count += 1
        displayCount(count)
    }

    private fun displayCount(count: Int) {
        countView.text = count.toString()
    }
}
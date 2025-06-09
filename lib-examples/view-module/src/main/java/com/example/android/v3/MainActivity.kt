package com.example.android.v3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android.R

// https://www.geeksforgeeks.org/livedata-in-android-architecture-components/
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_counter)
        val secondView: TextView = findViewById<TextView>(R.id.second)
        val startButton = findViewById<Button>(R.id.start)

        val viewModule = ViewModelProvider(this).get(MainViewModel::class.java)
        startButton.setOnClickListener {
            // click button to start the counter
            viewModule.startCounter()
        }

        viewModule.secondsValue().observe(this) {
            secondView.text = it.toString()
        }

        viewModule.isFinishedValue().observe(this) {
            startButton.isEnabled = it
        }
    }
}
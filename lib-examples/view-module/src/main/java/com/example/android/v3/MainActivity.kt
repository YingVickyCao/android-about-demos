package com.example.android.v3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.android.R
import com.example.android.v2.MainActivity
import kotlinx.coroutines.launch

// https://www.geeksforgeeks.org/livedata-in-android-architecture-components/
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e(TAG, "onCreate: " + System.identityHashCode(this))

        setContentView(R.layout.activity_counter)
        val secondView: TextView = findViewById<TextView>(R.id.second)
        val startButton = findViewById<Button>(R.id.start)

        // https://blog.csdn.net/qq_36699930/article/details/109698499
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

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return super.onRetainCustomNonConfigurationInstance()
    }
}
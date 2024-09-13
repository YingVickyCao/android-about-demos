package com.example.android.v2

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android.R

/***
 * Click button, refresh the count on the screen
 */
private const val TAG = "TimestampActivity"

class TimestampActivity : AppCompatActivity() {
    private lateinit var timestampView: TextView
    private lateinit var loaderView: View
    private lateinit var viewModule: TimestampViewModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timestamp)
        timestampView = findViewById(R.id.timestamp)
        loaderView = findViewById(R.id.loader)

        findViewById<View>(R.id.btn).setOnClickListener { clickBtn() }
        binds()
        initTimestamp()
    }

    private fun binds() {
        val repository = TimestampRepository(getTimestampService(), getCounterPreference(this))

        // TODO: How to create ViewModule ?
        // Create CountViewModule
        // Way 1
        viewModule = ViewModelProvider(this, TimestampViewModuleFactory(repository))[TimestampViewModule::class.java]

        // observe the toast value of View Module
        viewModule.toast.observe(this) { toast ->
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
        }
        // observe the timestamp value of View Module
        viewModule.timestamp.observe(this) { timestamp ->
            Log.e(TAG, "binds: timestamp=" + timestamp)
            displayTimestamp(timestamp.toString())
        }
        // observe the loader tag f View Module
        viewModule.loader.observe(this) { isShow ->
            loaderView.visibility = if (isShow) View.VISIBLE else View.GONE
        }
    }

    private fun initTimestamp() {
        viewModule.loadDefaultTimestamp()
    }

    private fun clickBtn() {
        viewModule.buttonClicked()
    }

    private fun displayTimestamp(count: String) {
        timestampView.text = count
    }
}
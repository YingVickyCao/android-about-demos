package com.example.android.v1

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.R

/***
 * Click button, refresh the count on the screen
 */
class MainActivity : AppCompatActivity() {
    private lateinit var countView: TextView
    private lateinit var viewModule: CounterViewModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countView = findViewById(R.id.count)

        findViewById<View>(R.id.btn).setOnClickListener { clickBtn() }
        bindViewModule()
        startCounter()
    }

    private fun bindViewModule() {
        // TODO: How to create ViewModule ?
        // Create CountViewModule
        // Way 1
        viewModule = ViewModelProvider(this).get(CounterViewModule::class.java)

        // observe the count value of View Module
        viewModule.count().observe(this, Observer { count ->
            displayCount(count.toString())
        })
        // observe the finished tag f View Module
        viewModule.finished().observe(this, Observer { finished ->
            if (finished) {
                displayCount("Finished")
            }
        })
    }

    private fun clickBtn() {
        viewModule.plusCount()
    }

    private fun displayCount(count: String) {
        countView.text = count
    }

    private fun startCounter() {
        // start counter in View Module
        viewModule.startCounter()
    }
}
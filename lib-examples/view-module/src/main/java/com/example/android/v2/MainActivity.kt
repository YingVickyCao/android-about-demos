package com.example.android.v2

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.android.R

/***
 * Click button, refresh the count on the screen
 */
class MainActivity : AppCompatActivity() {
    private lateinit var countView: TextView
    private lateinit var viewModule: CountViewModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countView = findViewById(R.id.count)

        findViewById<View>(R.id.btn).setOnClickListener { clickBtn() }
        bindViewModule()
        displayCount(viewModule.count)
    }

    private fun bindViewModule() {
        // TODO: How to create ViewModule ?
        // Create CountViewModule
        // Way 1
        viewModule = ViewModelProvider(this).get(CountViewModule::class.java)
    }

    private fun clickBtn() {
        plusCount()
    }

    private fun plusCount() {
        viewModule.count += 1
        displayCount(viewModule.count)
    }

    private fun displayCount(count: Int) {
        countView.text = count.toString()
    }
}
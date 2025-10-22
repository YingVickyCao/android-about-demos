package com.example.android.autoSave

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.android.R

private const val TAG = "SharedLiveDataExampleActivity"

class SaveExampleActivity : AppCompatActivity() {
    val viewModule: SaveViewModule by viewModels()
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved)

        findViewById<Button>(R.id.btn).setOnClickListener {
//            viewModule.increase()
            viewModule.setStu()
        }
        textView = findViewById<TextView>(R.id.content)

        viewModule.n1.observe(this, object : Observer<Long> {
            override fun onChanged(value: Long) {
                runOnUiThread {
                    textView.text = "" + value
                }
            }
        })

        viewModule.stu.observe(this, object : Observer<Stu> {
            override fun onChanged(value: Stu) {
                runOnUiThread {
                    textView.text = value.name
                }
            }
        })
    }

    private fun addNewObserver(){
        viewModule.stu.observe(this, object : Observer<Stu> {
            override fun onChanged(value: Stu) {
                runOnUiThread {
                    textView.text = value.name
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.e(TAG, "onSaveInstanceState: ", )
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e(TAG, "onRestoreInstanceState: ", )
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: ", )
    }
}
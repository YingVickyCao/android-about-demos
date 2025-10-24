package com.example.android.sticky_event

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

class StickyEventExampleActivity : AppCompatActivity() {
    val viewModule: AddNewObserverViewModule by viewModels()
    private lateinit var content: TextView
    private lateinit var content2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sticky_event)

        content = findViewById<TextView>(R.id.content)
        content2 = findViewById<TextView>(R.id.content2)

        findViewById<Button>(R.id.btn).setOnClickListener { viewModule.increase() }
        findViewById<Button>(R.id.addNewObserver).setOnClickListener { addNewObserver() }
        viewModule.n1.observe(this, object : Observer<Long> {
            override fun onChanged(value: Long) {
                runOnUiThread {
                    Log.e(TAG, "onChanged: $value", )
                    content.text = value.toString()
                }
            }
        })
    }

    private fun addNewObserver() {
        // LiveData的粘性问题：新加入的observer，它能收到最后一次的数据
        viewModule.n1.observe(this, object : Observer<Long> {
            override fun onChanged(value: Long) {
                runOnUiThread {
                    content2.text = value.toString()
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.e(TAG, "onSaveInstanceState: ")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e(TAG, "onRestoreInstanceState: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy: ")
    }
}
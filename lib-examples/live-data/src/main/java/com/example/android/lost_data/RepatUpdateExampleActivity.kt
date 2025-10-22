package com.example.android.lost_data

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.android.R
import io.reactivex.disposables.Disposable

private const val TAG = "LiveDataExampleActivity"

class LiveDataExampleActivity : AppCompatActivity() {

    private lateinit var countView: TextView
    private lateinit var viewModule: RepeatUpdateViewModule

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repeat_update)

        countView = findViewById(R.id.count)

        bindViewModule()
        findViewById<View>(R.id.btn).setOnClickListener {
            clickBtn()
//            Thread {
//                Thread.sleep(5000L)
//                clickBtn()
//            }.start()
        }
    }

    private fun bindViewModule() {
        viewModule = ViewModelProvider(this).get(RepeatUpdateViewModule::class.java)
//        sharedViewModel = ViewModelProvider(this).get(SharedViewModule::class.java)

        // observe the count value of View Module
        viewModule.count.observe(this, Observer { count ->
            Log.e(TAG, "observe: " + count)
            displayCount(count)
        })

        val disposable: Disposable = viewModule.subject.subscribe({ count ->
            Log.e(TAG, "subscribe: " + count)
            displayCount(count)
        })
        viewModule.disposables.add(disposable)
    }

    private fun displayCount(count: Int) {
        runOnUiThread {
            countView.text = count.toString()
        }
    }

    private fun clickBtn() {
        viewModule.updateByFastRepeat()
    }
}
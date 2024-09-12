package com.example.kotlin.coroutines.coroutines_guide_ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.coroutines.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md
// Guide to UI programming with coroutines

private const val TAG = "Example2Activity"

@SuppressLint("SetTextI18n")
@OptIn(DelicateCoroutinesApi::class)
class Example2_1Activity : AppCompatActivity() {
    private lateinit var hello: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example2)

        hello = findViewById(R.id.hello)

        setup(hello, findViewById(R.id.fab))
    }

    // Each time click the button, action will be invoked.
    // log:
    // E  action
    // E  action
    // E  action
    // E  action
    // E  action
    // E  action
    // E  action

    /**
     * Using actors within UI context - Extensions for coroutines
     */

    private fun setup(hello: TextView, fab: Button) {
        fab.onClick {  // start coroutine when button is clicked
            for (i in 10 downTo 1) {
                hello.text = "Countdown $i ..." // update text
                delay(1000L) // wait
            }
            hello.text = "Done!"
        }
    }

    private fun View.onClick(action: suspend () -> Unit) {
        setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                Log.e(TAG, "action")
                action()
            }
        }
    }
}
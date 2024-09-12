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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md
// Guide to UI programming with coroutines

private const val TAG = "Example3_2Activity"

@SuppressLint("SetTextI18n")
@OptIn(DelicateCoroutinesApi::class)
class Example3_1Activity : AppCompatActivity() {
    private lateinit var hello: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example3)

        hello = findViewById(R.id.hello)

        // log:
        // Skipped 32 frames!  The application may be doing too much work on its main thread.

        setup(hello, findViewById(R.id.fab))
    }


    /**
     * Blocking operations - The problem of UI freezes
     *
     */
    // I  Skipped 152 frames!  The application may be doing too much work on its main thread.
    // I  Thread[2,tid=29366,WaitingInMainSignalCatcherLoop,Thread*=0xb400006fd5aa96f0,peer=0x140f8020,"Signal Catcher"]: reacting to signal 3
    private fun View.onClick(action: suspend (View) -> Unit) {
        // launch one actor ti handle all the events on this node
        val eventActor =
            GlobalScope.actor<View>(Dispatchers.Main, capacity = Channel.CONFLATED) {
                for (event in channel) {
                    Log.e(TAG, "channel: action:${event.javaClass.name}")
                    action(event) // pass event to action
                }
            }
        // install a listener to active this actor
        setOnClickListener {
            Log.e(TAG, "onClick: trySend")
            eventActor.trySend(it)
        }
    }

    // We'll be computing larger and larger Fibonacci number each time the circle is clicked. To make the UI freeze more obvious, there is also a fast counting animation that is always running and is constantly updating the text in the main UI dispatcher:
    // Try clicking on the circle in this example. After around 30-40th click our naive computation is going to become quite slow and you would immediately see how the main UI thread freezes, because the animation stops running during UI freeze.
    private fun fib(x: Int): Int = if (x <= 1) x else fib(x - 1) + fib(x - 2)

    private fun setup(hello: TextView, fab: Button) {
        var result = "non"

        GlobalScope.launch(Dispatchers.Main) {
//                Log.e(TAG, "Dispatchers.Main:thread(${Thread.currentThread().name},${Thread.currentThread().id})" // Dispatchers.Main:thread(main,2))
            var count = 0
            while (true) {
                hello.text = "${++count}:$result"
                delay(100)
            }
        }

        // computer the next fib number of each click
        var x = 1
        fab.onClick {
            result = "flb($x) = ${fib(x)}"
            x++
        }
    }
}
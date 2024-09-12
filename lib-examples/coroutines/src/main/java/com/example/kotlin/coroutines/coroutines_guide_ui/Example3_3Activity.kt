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
import kotlinx.coroutines.withContext

// https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md#blocking-operations
// Guide to UI programming with coroutines

private const val TAG = "Example3_3Activity"

@SuppressLint("SetTextI18n")
@OptIn(DelicateCoroutinesApi::class)
class Example3_3Activity : AppCompatActivity() {
    private lateinit var hello: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example3)

        hello = findViewById(R.id.hello)

//        blockingOperations1()
        setup(hello, findViewById(R.id.fab))
    }

    /**
     * Blocking operations - Blocking operations
     *
     */
    private fun View.onClick(action: suspend (View) -> Unit) {
        // launch one actor ti handle all the events on this node
        val eventActor = GlobalScope.actor<View>(Dispatchers.Main, capacity = Channel.CONFLATED) {
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

    private fun setup(hello: TextView, fab: Button) {
        var result = "non"

        val job = GlobalScope.launch(Dispatchers.Main) { // launch in the main thread
            // Dispatchers.Main:thread(main,2)
            Log.e(TAG, "Dispatchers.Main:thread(${Thread.currentThread().name},${Thread.currentThread().id})")
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
            Log.e(TAG, "onClick: x=$x")
            x++
        }
        findViewById<View>(R.id.cancelParentJob).setOnClickListener { job.cancel() }
    }

    private suspend fun fib(x: Int): Int = withContext(Dispatchers.Default) {
        // E  Dispatchers.Default:thread(DefaultDispatcher-worker-1,62)
        Log.e(TAG, "Dispatchers.Default:thread(${Thread.currentThread().name},${Thread.currentThread().id})")
        if (x <= 1) x else fib(x - 1) + fib(x - 2)
        //        if (x <= 1) x else fibBlocking(x - 1) + fibBlocking(x - 2)
    }

//    private fun fibBlocking(x: Int): Int =
//        if (x <= 1) x else fibBlocking(x - 1) + fibBlocking(x - 2)
}
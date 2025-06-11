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
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md
// Guide to UI programming with coroutines

private const val TAG = "Example3_2Activity"

@SuppressLint("SetTextI18n")
@OptIn(DelicateCoroutinesApi::class)
class Example3_2Activity : AppCompatActivity() {
    private lateinit var hello: TextView

    val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example3)

        hello = findViewById(R.id.hello)

        setup(hello, findViewById(R.id.fab))

        findViewById<View>(R.id.cancelParentJob).setOnClickListener {  }
    }

    /**
     * Blocking operations -Structured concurrency, lifecycle and coroutine parent-child hierarchy
     */
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

//    private fun asyncShowData() =
//        scope.launch { // Is invoked  in UI context with Activity's scope as a parent
//            // actual implementation
//        }
//
//    suspend fun showIOData() {
//        val data = withContext(Dispatchers.IO) {
//            // compute data in background thread
//        }
//        withContext(Dispatchers.Main) {
//            // show data in UI
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
    }
    private fun setup(hello: TextView, fab: Button) = scope.launch { // launch in the main thread
        // Dispatchers.Main:thread(main,2)
        Log.e(TAG, "scope.launch (${Thread.currentThread().name},${Thread.currentThread().id})")
        var result = "non"
        // computer the next fib number of each click
        var x = 1
        fab.onClick {
            result = "flb($x) = ${fib(x)}"
            x++
        }
        var count = 0
        while (true) {
            hello.text = "${++count}:$result"
            delay(100)
        }
    }

    //  Background concurrent mark compact GC freed 3035KB AllocSpace bytes, 1(20KB) LOS objects, 87% free, 3408KB/27MB, paused 321us,5.631ms total 76.322ms
    suspend fun fib(x: Int): Int = withContext(Dispatchers.IO) {
        // E  Dispatchers.IO (DefaultDispatcher-worker-1,61)
        Log.e(TAG, "Dispatchers.IO (${Thread.currentThread().name},${Thread.currentThread().id})")
        if (x <= 1) x else fib(x - 1) + fib(x - 2)
    }
}
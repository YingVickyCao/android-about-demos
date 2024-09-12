package com.example.kotlin.coroutines.coroutines_guide_ui

import android.annotation.SuppressLint
import android.os.Bundle
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

// https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md#blocking-operations
// Guide to UI programming with coroutines

@SuppressLint("SetTextI18n")
@OptIn(DelicateCoroutinesApi::class)
class Example2_3Activity : AppCompatActivity() {
    private lateinit var hello: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example2)

        hello = findViewById(R.id.hello)

        // Now, if a circle is clicked while the animation is running, it restarts animation after the end of it. Just once. Repeated clicks while the animation is running are conflated and only the most recent event gets to be processed.
        setup(hello, findViewById(R.id.fab))
    }

    /**
     * Using actors within UI context - Event conflation
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

    private fun View.onClick(action: suspend (View) -> Unit) {
        val eventActor = GlobalScope.actor<View>(Dispatchers.Main, capacity = Channel.CONFLATED) { // <--- Changed here
            for (event in channel) action(event)
        }
        // install a listener to activate this actor
        setOnClickListener {
            eventActor.trySend(it)
        }
    }
}
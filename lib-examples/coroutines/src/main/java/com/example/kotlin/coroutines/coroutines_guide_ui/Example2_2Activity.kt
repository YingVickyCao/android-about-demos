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
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay

private const val TAG = "Example2_2Activity"

// https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md
// Guide to UI programming with coroutines

@SuppressLint("SetTextI18n")
@OptIn(DelicateCoroutinesApi::class)
class Example2_2Activity : AppCompatActivity() {
    private lateinit var hello: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example2)

        hello = findViewById(R.id.hello)

        // A better solution is to use an actor for tasks that should not be performed concurrently.

        // Each time click the button, if the action is invoking, not invoke action again
        // Try clicking repeatedly on a circle in this version of the code. The clicks are just ignored while the countdown animation is running.
        // log
        //  E  onClick: trySend
        // E  channel: action:androidx.appcompat.widget.AppCompatButton{6b094ae VFED..C.. ...P..ID 0,252-555,378 #7f0800b4 app:id/useActorsWithinUIContext}
        // E  onClick: trySend
        // E  onClick: trySend
        // E  onClick: trySend
        // E  onClick: trySend

        // log :
        //  E  onClick: trySend
        // E  channel: action:androidx.appcompat.widget.AppCompatButton{310824f VFED..C.. ...P.... 0,252-555,378 #7f0800b4 app:id/useActorsWithinUIContext}
        // E  onClick: trySend
        // E  channel: action:androidx.appcompat.widget.AppCompatButton{310824f VFED..C.. ...P..ID 0,252-555,378 #7f0800b4 app:id/useActorsWithinUIContext}
        setup(hello, findViewById(R.id.fab))
    }


    /**
     * Using actors within UI context - At most one concurrent job
     *
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
        // launch one actor
        val eventActor = GlobalScope.actor<View>(Dispatchers.Main) {
            for (event in channel) action(event)
        }
        // install a listener to activate this actor
        setOnClickListener {
            eventActor.trySend(it)
        }
    }
}
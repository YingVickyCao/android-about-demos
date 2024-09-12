package com.example.kotlin.coroutines.coroutines_guide_ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.coroutines.R
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md
// Guide to UI programming with coroutines

@SuppressLint("SetTextI18n")
@OptIn(DelicateCoroutinesApi::class)
class Example1Activity : AppCompatActivity() {
    private lateinit var hello: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example1)

        hello = findViewById(R.id.hello)

        setup(hello, findViewById(R.id.cancelUICoroutine))
    }

    /**
     * Launch UI coroutine
     *
     */
    // UI is not frozen while delay waits, because it does not block the UI thread - it just suspends the coroutine.
    private fun setup(hello: TextView, fab: Button) {
        val job = GlobalScope.launch(Dispatchers.Main) { // launch coroutine in the main thread
            for (i in 10 downTo 1) { // countdown from 10 to 1
                hello.text = "Countdown $i ..." // update text
                delay(1000L) // wait
            }
            hello.text = "Done!"
        }

        fab.setOnClickListener {
            cancelUICoroutine(job)
        }
    }

    /**
     * Cancel UI coroutine
     */
    private fun cancelUICoroutine(job: Job) {
        //  Job.cancel is completely thread-safe and non-blocking. It just signals the coroutine to cancel its job, without waiting for it to actually terminate. It can be invoked from anywhere. Invoking it on a coroutine that was already cancelled or has completed does nothing.
        job.cancel()
    }
}
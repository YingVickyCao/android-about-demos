package com.example.kotlin.coroutines.coroutines_guide_ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.coroutines.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import java.io.FileInputStream

// https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md
// Guide to UI programming with coroutines

private const val TAG = "Example1Activity"

@SuppressLint("SetTextI18n")
@OptIn(DelicateCoroutinesApi::class)
class Example1Activity : AppCompatActivity() {
    private lateinit var hello: TextView
    private lateinit var loadingView: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example1)

        hello = findViewById(R.id.hello)
        loadingView = findViewById<ProgressBar>(R.id.loadingView)

        setup(hello, findViewById(R.id.cancelUICoroutine))
    }

    /**
     * Launch UI coroutine
     *
     */
    // UI is not frozen while delay waits, because it does not block the UI thread - it just suspends the coroutine.
//    private fun setup(hello: TextView, fab: Button) {
//        val job = GlobalScope.launch(Dispatchers.Main) { // launch coroutine in the main thread
//            for (i in 10 downTo 1) { // countdown from 10 to 1
//                hello.text = "Countdown $i ..." // update text
//                delay(1000L) // wait
//            }
//            hello.text = "Done!"
//        }
//
//        fab.setOnClickListener {
//            cancelUICoroutine(job)
//        }
//    }
//    private fun setup(hello: TextView, fab: Button) {
//        val job = CoroutineScope(Dispatchers.IO).launch { // launch coroutine in the main thread
//            for (i in 10 downTo 1) { // countdown from 10 to 1
//                for (i in 10 downTo 1) { // countdown from 10 to 1
//                    runBlocking(Dispatchers.Main) {
//                        launch {
//                            hello.text = "Countdown $i ..." // update text
//                        }
//                    }
//                    delay(1000L) // wait
//                }
//            }
//        }
//
//        fab.setOnClickListener {
//            cancelUICoroutine(job)
//        }
//    }

//    private fun setup(hello: TextView, fab: Button) {
//        val job = CoroutineScope(Dispatchers.IO).launch { // launch coroutine in the main thread
//            for (i in 10 downTo 1) { // countdown from 10 to 1
//                CoroutineScope(Dispatchers.Main).launch {
//                    hello.text = "Countdown $i ..." // update text
//                }
//                delay(1000L) // wait
//            }
//        }
//
//        fab.setOnClickListener {
//            cancelUICoroutine(job)
//        }
//    }

    private fun setup2(hello: TextView, fab: Button) {
        // launch multiple times
        CoroutineScope(Dispatchers.IO).launch {
            coroutineScope {
                var count = 1
//                for (i in 10 downTo 1) {
                launch(Dispatchers.IO) {
                    delay(2000L)
                    count = 2
                    launch(Dispatchers.Main) {
                        Log.e("Test", "setup1: $count")
                        hello.text = "Countdown $count" // update text
//                    }

                    }
                }
                launch(Dispatchers.IO) {
                    delay(3000L)
                    count = 20
                    launch(Dispatchers.Main) {
                        Log.e("Test", "setup2: $count")
                        hello.text = "Countdown $count" // update text
//                    }

                    }
                }

                launch(Dispatchers.Main) {
                    Log.e("Test", "setup3: $count")
                    hello.text = "Countdown $count" // update text
//                    }

                }

            }
        }
//            fab.setOnClickListener {
//                cancelUICoroutine(job)
//            }
    }

//    private fun setup(hello: TextView, fab: Button) {
//        //  job.join()
//        fab.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val job = CoroutineScope(Dispatchers.IO).launch {
//                    delay(5_000)
//                    Log.e(TAG, "setup: World")
//                }
//
//                Log.e(TAG, "setup: Hello")
//                job.join()
//                Log.e(TAG, "setup: done")
//            }
//        }
//    }

//    private fun setup(hello: TextView, fab: Button) {
//        // Deffered = async
//        fab.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val deferred = async {
//                    delay(2_000L)
//                    Log.e(TAG, "setup: loading")
//                    return@async 100
//                }
//                Log.e(TAG, "setup: waiting.....")
//                launch(Dispatchers.Main) {
//                    val result = deferred.await()
//                    hello.text = "$result"
//                }
//                Log.e(TAG, "setup: done")
//            }
//        }
//    }

//    private fun setup(hello: TextView, fab: Button) {
//        // List<Deferred<Int>>
//        CoroutineScope(Dispatchers.IO).launch {
//            val deferreds: List<Deferred<Int>> = (1..3).map {
//                async {
//                    delay(2_000)
//                    Log.e(TAG, "setup: $it")
//                    it
//                }
//            }
//
//            val result = deferreds.awaitAll().sum()
//            launch(Dispatchers.Main) {
//                hello.text = "$result"
//                Log.e(TAG, "setup: $result")
//            }
//        }
//    }


    // cancel
//    private fun setup(hello: TextView, fab: Button) {
//        fab.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val job = launch {
//                    while (isActive) {
//                        delay(1 * 1_000)
//                        Log.e(TAG, "setup: delay")
//                    }
//                }
//                Log.e(TAG, "setup: waitig")
//                delay(5_000L)
//                job.cancelAndJoin()
//                Log.e(TAG, "setup:closed ")
//            }
//        }
//    }

    // close resources in finally
//    private fun setup(hello: TextView, fab: Button) {
//        fab.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val job = launch {
//                    try {
//                        while (true) {
//                            delay(1 * 1_000)
//                            Log.e(TAG, "setup: delay")
//                        }
//                    } catch (ex: Exception) { // JobCancellationException
//                        Log.e(TAG, "setup: $ex")
//                    } finally {
//                        Log.e(TAG, "setup: close resources", )
//                    }
//
//                }
//                Log.e(TAG, "setup: waiting")
//                delay(5_000L)
//                job.cancelAndJoin()
//                Log.e(TAG, "setup:closed ")
//            }
//        }
//    }


    // Run non-cancellable block
//    private fun setup(hello: TextView, fab: Button) {
//        fab.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                val job = launch {
//                    try {
//                        while (true) {
//                            delay(1 * 1_000)
//                            Log.e(TAG, "setup: delay")
//                        }
//                    } catch (ex: Exception) { // JobCancellationException
//                        Log.e(TAG, "setup: $ex")
//                    } finally {
//                        withContext(NonCancellable) {
//                            Log.e(TAG, "setup: job, withContext(NonCancellable)")
//                            delay(1_000L)
//                            Log.e(TAG, "setup: job, delayed ")
//                        }
//                        Log.e(TAG, "setup: close resources")
//
////                        Log.e(TAG, "setup: job invoke delay")
////                        delay(2_000L)
////                        Log.e(TAG, "setup: job, delayed ") // not invoked
////                        Log.e(TAG, "setup: close resources") // not invoked
//                    }
//
//                }
//                Log.e(TAG, "setup: waiting")
//                delay(5_000L)
//                job.cancelAndJoin()
//                Log.e(TAG, "setup:closed ")
//            }
//        }
//    }


    // timeout
//    private fun setup(hello: TextView, fab: Button) {
//        fab.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                withTimeout(1300) {
//                    repeat(100) { i ->
//                        Log.e(TAG, "sleeping")
//                        delay(500L)
//
//                    }
//                }
//
//            }
//        }
//    }

    // close if timeout
//    private fun setup(hello: TextView, fab: Button) {
//        fab.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                repeat(10_000) { i ->
//                    launch {
//                        Log.e(TAG, "sleeping")
//                        val result: FileInputStream = withTimeout(100) {
//                            FileInputStream("");
//                        }
//                        result.close()
//                    }
//                }
//            }
//        }
//    }
//}


    // suspend fun
//    private fun setup(hello: TextView, fab: Button) {
//        fab.setOnClickListener {
//
//            suspend fun f1(): Int {
//                delay(1_000L)
//                return 13
//            }
//
//            suspend fun f2(): Int {
//                delay(2_000)
//                return 12
//            }
//
//            CoroutineScope(Dispatchers.IO).launch {
//                val r1 = f1()
//                val r2 = f2()
//                launch(Dispatchers.Main) {
//                    hello.text = "$r1 + $r2"
//                }
//            }
//        }
//    }

    // concurrent using async - suspend fun + async
//    private fun setup(hello: TextView, fab: Button) {
//        fab.setOnClickListener {
//
//            suspend fun f1(): Int {
//                delay(1_000L)
//                return 13
//            }
//
//            suspend fun f2(): Int {
//                delay(1_000)
//                return 12
//            }
//
//            CoroutineScope(Dispatchers.IO).launch {
//                val f1 = async {
//                    f1()
//                }
//                val f2 = async {
//                    f2()
//                }
//                launch(Dispatchers.Main) {
//                    hello.text = "${f1.await() + f2.await()} "
//                }
//            }
//        }
//    }

    // concurrent using lazy async - suspend fun + async
//    private fun setup(hello: TextView, fab: Button) {
//        fab.setOnClickListener {
//
//            suspend fun f1(): Int {
//                delay(1_000L)
//                return 13
//            }
//
//            suspend fun f2(): Int {
//                delay(1_000)
//                return 12
//            }
//
//            CoroutineScope(Dispatchers.IO).launch {
//                val f1 = async(start = CoroutineStart.LAZY) {
//                    f1()
//                }
//                val f2 = async(start = CoroutineStart.LAZY) {
//                    f2()
//                }
//                // start the coroutine only when the result required by wait
//                val result = f1.await() + f2.await()
//                launch(Dispatchers.Main) {
//                    hello.text = "${result} "
//                }
//            }
//        }
//    }

    // Asynchronous Flow
    private fun setup(hello: TextView, fab: Button) {
        fab.setOnClickListener {
            test(hello, fab)
        }
    }

//    private fun test(hello: TextView, fab: Button) {
//        val list: List<Int> = listOf(1, 2, 3)
//        list.forEach {
//            hello.text = "$it"
//            Log.e(TAG, "test:$it ")
//        }
//    }


    // Asynchronous Flow - Sequences - synchronously computed values and the value is yield one by one
//    private fun test(hello: TextView, fab: Button) {
//        val sequence: Sequence<Int> = sequence { // synchronously computed values
//            for (i in 1..3) {
//                Thread.sleep(2_000)
//                yield(i)
//            }
//        }
//        sequence.forEach {
//            runOnUiThread { // block the main UI
//                var value = hello.text
//                value = "$value" +
//                        "\n" +
//                        "$it"
//                hello.text = value
//                Log.e(TAG, "test:$it ")
//            }
//        }

//        CoroutineScope(Dispatchers.IO).launch {
//            val sequence: Sequence<Int> = sequence {
//                for (i in 1..3) {
//                    Thread.sleep(2_000)
//                    yield(i)
//                }
//            }
//            sequence.forEach {
//                runOnUiThread {
//                    hello.text = "${hello.text}\n$it"
//                    Log.e(TAG, "test:$it ")
//                }
//            }
//        }
//    }

    // Suspending functions - Suspending functions
//    private fun test(hello: TextView, fab: Button) {
//        hello.text = ""
//        suspend fun simple(): List<Int> {
//            delay(1_000)
//            return listOf(1, 2, 3) // return values at once
//        }
//        CoroutineScope(Dispatchers.IO).launch {
//            simple().forEach {
//                launch(Dispatchers.Main) {
//                    hello.text = "${hello.text}\n $it"
//                    Log.e(TAG, "test:$it ")
//                }
//            }
//        }
//    }

    // Suspending functions - Flows, computed asynchronously
//    private fun test(hello: TextView, fab: Button) {
//        fun simple(): Flow<Int> = flow {
//            Log.e(TAG, "test: flow")
//            for (i in 1..3) {
//                delay(1_000)
//                Log.e(TAG, "test: flow emit $i")
//                emit(i) // sended
//            }
//        }
//
//        CoroutineScope(Dispatchers.IO).launch {
//            Log.e(TAG, "test: [0]")
//            // the code in flow is not run until the flow is collected
//            val streams: Flow<Int> = simple()
//            Log.e(TAG, "test: [2]")
//            streams.collect { // receive
//                launch(Dispatchers.Main) {
//                    Log.e(TAG, "test: collected $it")
//                    hello.text = "$it"
//                    Log.e(TAG, "test:$it ")
//                }
//            }
//        }
//    }


    // Suspending functions - Flows, cancel
    // https://kotlinlang.org/docs/flow.html#flow-cancellation-basics
//    private fun test(hello: TextView, fab: Button) {
//        fun simple(): Flow<Int> = flow {
//            for (i in 1..3) {
//                emit(i)
//                delay(1_000)
//            }
//        }
//        CoroutineScope(Dispatchers.IO).launch {
//            Log.e(TAG, "test: [0]]")
//            withTimeoutOrNull(1_000) { // timeout after 1 second
//                Log.e(TAG, "test: starting")
//                val flow: Flow<Int> = simple()
//                flow.collect {
//                    launch(Dispatchers.Main) {
//                        Log.e(TAG, "test: collected $it")
//                        hello.text = "$it"
//                    }
//                }
//            }
//            Log.e(TAG, "test: done")
//        }
//    }

    // Suspending functions - flowOf -
    // builder defines a flow that emits a fixed set of values.
    // collections and sequences can be converted to flows
//    private fun test(hello: TextView, fab: Button) {
//        loadingView.visibility = View.VISIBLE;
//        CoroutineScope(Dispatchers.IO).launch {
//            (1..3).asFlow().collect { value ->
//                launch(Dispatchers.Main) {
//                    hello.text = "$value"
//                    loadingView.visibility = View.GONE
//                }
//            }
//        }
//    }

    // Suspending functions - Intermediate flow operators
    // flow -> map
//    private fun test(hello: TextView, fab: Button) {
//        loadingView.visibility = View.VISIBLE
//        suspend fun performRequest(request: Int): String {
//            delay(1000)
//            return "request $request"
//        }
//        CoroutineScope(Dispatchers.IO).launch {
//            (1..3).asFlow()
//                .map { it -> performRequest(it) }
//                .collect { response ->
//                    launch(Dispatchers.Main) {
//                        hello.text = "${hello.text} \n$response"
//                        loadingView.visibility = View.GONE
//                    }
//                }
//        }
//    }

    // Suspending functions - Intermediate flow operators
    // flow -> filter
    // https://blog.csdn.net/chuyouyinghe/article/details/137056200
    // https://kotlinlang.org/docs/flow.html#intermediate-flow-operators
//    private fun test(hello: TextView, fab: Button) {
//        loadingView.visibility = View.VISIBLE
//        suspend fun performRequest(request: Int): String {
//            delay(1000)
//            return "request $request"
//        }
//        CoroutineScope(Dispatchers.IO).launch {
//            (1..15).asFlow()
//                .filter { it -> it > 10 }
//                .map { performRequest(it) }
//                .collect { response ->
//                    launch(Dispatchers.Main) { // 11,...,15
//                        hello.text = "${hello.text} \n$response"
//                        loadingView.visibility = View.GONE
//                    }
//                }
//        }
//    }

    // Suspending functions - Intermediate flow operators
    // flow - flow -> filterNot
    // https://blog.csdn.net/chuyouyinghe/article/details/137056200
    // https://kotlinlang.org/docs/flow.html#intermediate-flow-operators
//    private fun test(hello: TextView, fab: Button) {
//        loadingView.visibility = View.VISIBLE
//        suspend fun performRequest(request: Int): String {
//            delay(1000)
//            return "request $request"
//        }
//        CoroutineScope(Dispatchers.IO).launch {
//            (1..15).asFlow()
//                .filterNot { it -> it > 10 }
//                .map { performRequest(it) }
//                .collect { response ->
//                    launch(Dispatchers.Main) { // 1,...,10
//                        hello.text = "${hello.text} \n$response"
//                        loadingView.visibility = View.GONE
//                    }
//                }
//        }
//    }

    // https://blog.csdn.net/chuyouyinghe/article/details/137056200
    // flow - map ： 从一个集转成成另一个集合，一对一映射
//    private fun test(hello: TextView, fab: Button) {
//        listOf(1, 2, 3).map {
//            hello.text = "${hello.text}\n$it"
//        }
//    }

    // flow - flapMap: 将每一个元素转换另一个项目的集合，然后将这些集合平坦地化为一个单一集合。
//    private fun test(hello: TextView, fab: Button) {
//        val words = listOf("Kotlin is fun", "I love Kotlin").flatMap {
//            it.split(" ")
//        }
//        // words
//        // 0 = "Kotlin"
//        //1 = "is"
//        //2 = "fun"
//        //3 = "I"
//        //4 = "love"
//        //5 = "Kotlin"
//        words.map {
//            hello.text = "${hello.text}\n$it"
//        }
//    }

    // flow - transform:  1 imitate simple transformations like map and filter  2 implement more complex transformations.
    private fun test(hello: TextView, fab: Button) {
        CoroutineScope(Dispatchers.IO).launch {
            (1..3).asFlow()
                .transform {
                    emit("Makeing request $it")
                    emit(performRequest(it))
                }
                .collect {
                    launch(Dispatchers.Main) {
                        hello.text = "${hello.text}\n$it"
                    }
                }
        }
    }

    suspend fun performRequest(request: Int): String {
        delay(1000)
        return "request $request"
    }


    /**
     * Cancel UI coroutine
     */
    private fun cancelUICoroutine(job: Job) {
        //  Job.cancel is completely thread-safe and non-blocking. It just signals the coroutine to cancel its job, without waiting for it to actually terminate. It can be invoked from anywhere. Invoking it on a coroutine that was already cancelled or has completed does nothing.
        job.cancel()
    }
}
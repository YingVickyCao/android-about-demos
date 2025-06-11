package com.example.kotlin.coroutines.coroutines_guide_ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.coroutines.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.io.IOException

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
//    private fun test(hello: TextView, fab: Button) {
//        CoroutineScope(Dispatchers.IO).launch {
//            (1..3).asFlow()
//                .transform {
//                    emit("Makeing request $it")
//                    emit(performRequest(it))
//                }
//                .collect {
//                    launch(Dispatchers.Main) {
//                        hello.text = "${hello.text}\n$it"
//                    }
//                }
//        }
//    }

    // flow - take th size limit like then cancel execution of the flow, and AbortFlowException
//    private fun test(hello: TextView, fab: Button) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val flow: Flow<Int> = flow {
//                try {
//                    launch(Dispatchers.Main) {
//                        loadingView.visibility = View.VISIBLE
//                    }
//                    emit(1)
//                    launch(Dispatchers.Main) {
//                        loadingView.visibility = View.VISIBLE
//                    }
//                    delay(1_000L)
//                    emit(2)
//                    launch(Dispatchers.Main) {
//                        loadingView.visibility = View.VISIBLE
//                    }
//                    delay(1_000L)
//                    emit(3)
//                    launch(Dispatchers.Main) {
//                        loadingView.visibility = View.VISIBLE
//                    }
//                    delay(1_000L)
//                    emit(4)
//                    println("This line will not execute")
//                } catch (ex: Exception) { // kotlinx.coroutines.flow.internal.AbortFlowException: Flow was aborted, no more elements needed
//                    println("flow occurred exception: $ex")
//                } finally {
//                    println("finally, should close the resources")
//                }
//            }
//            flow.take(3) // take only the first two
//                .collect {
//                    launch(Dispatchers.Main) {
//                        hello.text = "${hello.text}\n$it"
//                        loadingView.visibility = View.GONE
//                    }
//                }
//        }
//    }

    // Flows are sequential
//    private fun test(hello: TextView, fab: Button) {
//        CoroutineScope(Dispatchers.IO).launch {
//            (1..5).asFlow()
//                .filter {
//                    println("Filter $it")
//                    it % 2 == 0
//                }.map {
//                    println("Map $it")
//                    it
//                }
//                .collect {
//                    println("collected $it")
//                    launch(Dispatchers.Main) {
//                        hello.text = "${hello.text}\n$it"
//                    }
//                }
//        }
//    }

    // Flows are sequential
//    private fun test(hello: TextView, fab: Button) {
//        CoroutineScope(Dispatchers.IO).launch {
//            withContext(Dispatchers.Default.limitedParallelism(5)) {
//            }
//        }
//    }

    // Flows - flowOn, change the context the flow emission
//    private fun test(hello: TextView, fab: Button) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val flow: Flow<Int> = flow {
//                for (i in 1..3) {
//                    delay(1_000) // mock cpu-consuming work
//                    println("${Thread.currentThread().id} , ${Thread.currentThread().name}") // DefaultDispatcher-worker-1
//                    emit(i)
//                }
//            }.flowOn(Dispatchers.Default)
//            flow.collect {
//                println("collected:${Thread.currentThread().id} , ${Thread.currentThread().name}") // DefaultDispatcher-worker-1
//                launch(Dispatchers.Main) {
//                    hello.text = "${hello.text}\n$it"
//                }
//            }
//        }
//    }

    // Buffering
//    private fun test(hello: TextView, fab: Button) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val flow: Flow<Int> = flow {
//                for (i in 1..3) {
//                    delay(1_100) // flow is slow
//                    println("send $i")
//                    emit(i)
//                }
//            }
//            val time = measureTimeMillis {
//                flow.collect {
//                    delay(3_000L) // collector is also slow
//                    println("$it are collected")
//                    launch(Dispatchers.Main) {
//                        hello.text = "${hello.text}\n$it"
//                    }
//                }
//            }
//            println("Collected in $time ms")
//        }


//        CoroutineScope(Dispatchers.IO).launch {
//            val flow: Flow<Int> = flow {
////                for (i in 1..3) {
////                    // // 假装我们异步等待了 1 秒
////                    delay(1_100) // flow is slow
////                    println("send $i")
////                    emit(i) // 发射下一个值
////                }
////            }
////            val time = measureTimeMillis {
////                flow
////                    //  // 缓冲发射项，无需等待
////                    .buffer() // buffer - emit the code of flow concurrently
////                    .collect {
////                        // // 假装我们花费 300 毫秒来处理它
////                        delay(3_000L) // collector is also slow
////                        println("$it are collected")
////                        launch(Dispatchers.Main) {
////                            hello.text = "${hello.text}\n$it"
////                        }
////                    }
////            }
//            println("Collected in $time ms")
//        }
//    }

    // conflate - skip intermediate values 跳过中间项只处理最新数据
//    private fun test(hello: TextView, fab: Button) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val flow: Flow<Int> = flow {
//                for (i in 1..3) {
//                    // // 假装我们异步等待了 1 秒
//                    delay(1_100) // flow is slow
//                    println("send $i")
//                    emit(i) // 发射下一个值
//                }
//            }
//            val time = measureTimeMillis {
//                flow
//                    .conflate() // buffer - emit the code of flow concurrently
//                    .collect {
//                        println("$it is comming")
//                        // 假装我们花费 300 毫秒来处理它
//                        delay(3_000L) // collector is also slow
//                        println("$it are collected")
//                        launch(Dispatchers.Main) {
//                            hello.text = "${hello.text}\n$it"
//                        }
//                    }
//            }
//            println("Collected in $time ms")
//        }
//    }

    // collectLatest - Processing the latest value https://kotlinlang.org/docs/flow.html#processing-the-latest-value
//    private fun test(hello: TextView, fab: Button) {
//        CoroutineScope(Dispatchers.IO).launch {
//            val flow: Flow<Int> = flow {
//                for (i in 1..3) {
//                    // // 假装我们异步等待了 1 秒
//                    delay(1_100) // flow is slow
//                    println("send $i")
//                    emit(i) // 发射下一个值
//                }
//            }
//            val time = measureTimeMillis {
//                flow.collectLatest {
//                    println("$it is comming")
//                    // 假装我们花费 300 毫秒来处理它
//                    delay(3_000L) // collector is also slow
//                    println("$it are collected")
//                    launch(Dispatchers.Main) {
//                        hello.text = "${hello.text}\n$it"
//                    }
//                }
//            }
//            println("Collected in $time ms")
//        }
//    }

    // Zip - combines the corresponding values of two flows  https://kotlinlang.org/docs/flow.html#zip
//    private fun test(hello: TextView, fab: Button) {
//        val flow1 = (1..3).asFlow()
//        val flow2 = flowOf("one", "two", "three")
//        CoroutineScope(Dispatchers.IO).launch {
//            flow1.zip(flow2) { a, b -> "$a - $b" }
//                .collect {
//                    println("$it are collected")
// 1 -> one at 452 ms from start
//2 -> one at 651 ms from start
//2 -> two at 854 ms from start
//3 -> two at 952 ms from start
//3 -> three at 1256 ms from start
//                    launch(Dispatchers.Main) {
//                        hello.text = "${hello.text}\n$it"
//                    }
//                }
//        }
//    }

    // combine
//    private fun test(hello: TextView, fab: Button) {
//        val flow1 = (1..3).asFlow().onEach { delay(3_000L) }
//        val flow2 = flowOf("one", "two", "three").onEach { delay(4_000L) }
//        CoroutineScope(Dispatchers.IO).launch {
////            val ms = measureTimeMillis {
////                val startTime = System.currentTimeMillis()
////                flow1.zip(flow2) { a, b -> "$a - $b" } //  zip 操作符合并它们，但仍会产生相同的结果， 尽管每 400 毫秒打印一次结果
////                    .collect {
////                        println("$it at ${System.currentTimeMillis() - startTime} ms from start")
////                        //  1 - one are collected
////                        //  2 - two are collected
////                        //  3 - three are collected
////                        launch(Dispatchers.Main) {
////                            hello.text = "${hello.text}\n$it"
////                        }
////                    }
////            }
////            println("Collected in $ms ms")
//
//            val ms = measureTimeMillis {
//                val startTime = System.currentTimeMillis()
//                flow1.combine(flow2) { a, b -> "$a - $b" } //  使用“combine”组合单个字符串
//                    .collect {
//                        println("$it at ${System.currentTimeMillis() - startTime} ms from start")
//                        //  1 - one are collected
//                        //  2 - two are collected
//                        //  3 - three are collected
//                        launch(Dispatchers.Main) {
//                            hello.text = "${hello.text}\n$it"
//                        }
//                    }
//            }
//            println("Collected in $ms ms")
//        }
//    }

    //  a flow of flows
    private fun no_flattenFlows(hello: TextView, fab: Button) {
        CoroutineScope(Dispatchers.IO).launch {
            fun requestFlow(i: Int): Flow<String> = flow {
                emit("$i: First")
                delay(500) // wait 500 ms
                emit("$i: Second")
            }

            (1..3).asFlow()
                .map {
                    requestFlow(it)
                }.collect {
                    println("$it are collected") // kotlinx.coroutines.flow.SafeFlow@c88b442 are collected
                    launch(Dispatchers.Main) {
                        hello.text = "${hello.text}\n$it"
                    }
                }
        }
    }

    //  a flow of flows (Flow<Flow<String>>) that needs to be flattened into a single flow for further processing.
    // flatMapConcat
    private fun flatMapConcat(hello: TextView, fab: Button) {
        CoroutineScope(Dispatchers.IO).launch {
            fun requestFlow(i: Int): Flow<String> = flow {
                emit("$i: First")
                delay(500) // wait 500 ms
                emit("$i: Second")
            }

            (1..3).asFlow()
                .flatMapConcat {
                    requestFlow(it)
                }.collect {
                    // 1: First are collected
                    // 1: Second are collected
                    // 2: First are collected
                    // 2: Second are collected
                    // 3: First are collected
                    // 3: Second are collected
                    println("$it are collected")
                    launch(Dispatchers.Main) {
                        hello.text = "${hello.text}\n$it"
                    }
                }
        }
    }

    private fun flatMapMerge(hello: TextView, fab: Button) {
        CoroutineScope(Dispatchers.IO).launch {
            fun requestFlow(i: Int): Flow<String> = flow {
                emit("$i: First")
                delay(500) // wait 500 ms
                emit("$i: Second")
            }

            (1..3).asFlow()
                .flatMapMerge {
                    requestFlow(it)
                }.collect {
                    // 1: First are collected
                    // 3: First are collected
                    // 2: First are collected
                    // 1: Second are collected
                    // 3: Second are collected
                    // 2: Second are collected
                    println("$it are collected")
                    launch(Dispatchers.Main) {
                        hello.text = "${hello.text}\n$it"
                    }
                }
        }
    }

    private fun flatMapLatest(hello: TextView, fab: Button) {
        CoroutineScope(Dispatchers.IO).launch {
            fun requestFlow(i: Int): Flow<String> = flow {
                emit("$i: First")
                delay(500) // wait 500 ms
                emit("$i: Second")
            }

            (1..3).asFlow().onEach { delay(100) }
                .flatMapLatest {
                    requestFlow(it)
                }.collect {
                    // 1: First are collected
                    // 3: First are collected
                    // 2: First are collected
                    // 1: Second are collected
                    // 3: Second are collected
                    // 2: Second are collected
                    println("$it are collected")
                    launch(Dispatchers.Main) {
                        hello.text = "${hello.text}\n$it"
                    }
                }
        }
    }

    private fun root_coroutine_throw_exception() {
        CoroutineScope(Dispatchers.IO).launch {
            val job = GlobalScope.launch { // root coroutine with launch
                println("Throwing exception from launch")
                // App crashed
                throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
            }
            job.join()
            println("Joined failed job")
            val deferred = GlobalScope.async { // root coroutine with async
                println("Throwing exception from async")
                throw ArithmeticException() // Nothing is printed, relying on user to call await
            }
            try {
                deferred.await()
                println("Unreached")
            } catch (e: ArithmeticException) {
                println("Caught ArithmeticException")
            }

        }
    }

    private fun root_coroutine_throw_exception2() {
        CoroutineScope(Dispatchers.IO).launch {
            val job = GlobalScope.launch { // root coroutine with launch
                println("Throwing exception from launch")
            }
            job.join()
            println("Joined failed job")
            val deferred = GlobalScope.async { // root coroutine with async
                println("Throwing exception from async")
                throw ArithmeticException() // Nothing is printed, relying on user to call await
            }
            try {
                deferred.await()
                println("Unreached")
            } catch (e: ArithmeticException) {
                // TODO: app not crashed
                println("Caught ArithmeticException")
            }

        }
    }

    private fun root_coroutine_throw_exception3() {
        CoroutineScope(Dispatchers.IO).launch {
            val job = GlobalScope.launch { // root coroutine with launch
                println("Throwing exception from launch")
            }
            job.join()
            println("Joined failed job")
            val deferred = GlobalScope.async { // root coroutine with async
                println("Throwing exception from async")
                // TODO: app crashed
                throw ArithmeticException() // Nothing is printed, relying on user to call await
            }

            deferred.await()
            println("Unreached")

        }
    }


    private fun root_coroutine_throw_exception4() {
        CoroutineScope(Dispatchers.IO).launch {
            val handler = CoroutineExceptionHandler { _, exception ->
                println("CoroutineExceptionHandler got $exception")
            }

            val job = GlobalScope.launch(handler) { // root coroutine with launch
                println("Throwing exception from launch")
                throw AssertionError()
            }
//            job.join()

            println("Joined success job")
            val deferred = GlobalScope.async(handler) { // root coroutine with async
                println("Throwing exception from async")
                // TODO: app not crashed
                throw ArithmeticException() // Nothing is printed, relying on user to call await
            }
            // Suspends current coroutine until all given jobs are complete.
            joinAll(job, deferred)
            println("Reached")
        }
    }

    private fun root_coroutine_throw_exception5() {
        CoroutineScope(Dispatchers.IO).launch {
            val handler = CoroutineExceptionHandler { _, exception ->
                println("CoroutineExceptionHandler got $exception")
            }

            val job = GlobalScope.launch(handler) { // root coroutine with launch
                println("Throwing exception from launch")
                throw AssertionError()
            }
            job.join()

            println("Joined success job")
            val deferred = GlobalScope.async(handler) { // root coroutine with async
                println("Throwing exception from async")
                // TODO: app crashed
                throw ArithmeticException() // Nothing is printed, relying on user to call await
            }
            deferred.await()
            println("Unreached")

        }
    }

    private fun root_coroutine_throw_exception6() {
        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                val child = launch {
                    try {
                        delay(Long.MAX_VALUE)
                    } finally {
                        println("Child is cancelled")
                    }
                }
                // 同jS yield ： “return the value，and continue when you next enter
                // 在此处返回，并且在你下次进入时 从此处继续
                yield()
                println("Cancelling child")
                child.cancel()
                child.join()
                yield()
                println("Parent is not cancelled")
            }
            job.join()

        }
    }

    private fun Cancellation_exceptions() {
        CoroutineScope(Dispatchers.IO).launch {
            val job = launch {
                val child = launch {
                    try {
                        delay(Long.MAX_VALUE)
                    } catch (ex: Exception) {
                        println("JobCancellationException catched : $ex")
                    } finally {
                        println("Child is cancelled")
                    }
                }
                yield()
                println("Cancelling child")
                child.cancel() // CancellationException
                child.join()
                yield()
                println("Parent is not cancelled")
            }
            job.join()
        }
    }

    private fun Cancellation_exceptions2() {
        CoroutineScope(Dispatchers.IO).launch {
            val handler = CoroutineExceptionHandler { _, exception ->
                println("CoroutineExceptionHandler got $exception")
            }
            val job = GlobalScope.launch(handler) {
                launch { // the first child
                    try {
                        delay(Long.MAX_VALUE)
                    } catch (ex: Exception) {
                        println("JobCancellationException catched : $ex")
                    } finally {
                        withContext(NonCancellable) {
                            println("Children are cancelled, but exception is not handled until all children terminate")
                            delay(100)
                            println("The first child finished its non cancellable block")
                        }
                    }
                }
                launch { // the second child
                    delay(10)
                    println("Second child throws an exception")
                    throw ArithmeticException()
                }
            }
            job.join()
        }
    }

    private fun test_Exceptions_aggregation() {
        CoroutineScope(Dispatchers.IO).launch {
            val handler = CoroutineExceptionHandler { _, exception ->
                // I  CoroutineExceptionHandler got java.io.IOException with suppressed [java.lang.ArithmeticException]
                println("CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}") // IOException
            }
            val job = GlobalScope.launch(handler) {
                launch {
                    try {
                        delay(Long.MAX_VALUE) // it gets cancelled when another sibling fails with IOException
                    } catch (ex: Exception) {
                        // I  JobCancellationException catched : kotlinx.coroutines.JobCancellationException: Parent job is Cancelling; job=StandaloneCoroutine{Cancelling}@c88b442
                        println("JobCancellationException catched : $ex")
                    } finally {
                        throw ArithmeticException() // the second exception
                    }
                }
                launch {
                    delay(100)
                    println("Second child throws an exception")
                    throw IOException() // the first exception
                }
                delay(Long.MAX_VALUE)
            }
            job.join()
        }
    }

    // https://kotlinlang.org/docs/exception-handling.html#supervision-job
    private fun test_supervision_job() {
        CoroutineScope(Dispatchers.IO).launch {
            val supervisor = SupervisorJob()
            with(CoroutineScope(coroutineContext + supervisor)) {
                // launch the first child -- its exception is ignored for this example (don't do this in practice!)
                val firstChild = launch(CoroutineExceptionHandler { _, _ -> }) {
                    println("The first child is failing")
                    throw AssertionError("The first child is cancelled")
                }
                // launch the second child
                val secondChild = launch {
                    firstChild.join()
                    // Cancellation of the first child is not propagated to the second child
                    println("The first child is cancelled: ${firstChild.isCancelled}, but the second one is still active")
                    try {
                        delay(Long.MAX_VALUE)
                    } catch (ex: Exception) {
                        // I  JobCancellationException catched : kotlinx.coroutines.JobCancellationException: Job was cancelled; job=SupervisorJobImpl{Cancelling}@a21b990
                        println("JobCancellationException catched : $ex")
                    } finally {
                        // But cancellation of the supervisor is propagated
                        println("The second child is cancelled because the supervisor was cancelled")
                    }
                }
                // wait until the first child fails & completes
                println("firstChild joined")
                firstChild.join()
                println("Cancelling the supervisor")
                supervisor.cancel()
                println("parent is not cancelled")
                secondChild.join()
            }
        }
    }

    private fun test_supervision_scope() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                supervisorScope {
                    val child = launch {
                        try {
                            println("The child is sleeping")
                            delay(Long.MAX_VALUE)
                        } catch (ex: Exception) {
                            // JobCancellationException catched : kotlinx.coroutines.JobCancellationException: Parent job is Cancelling; job=SupervisorCoroutine{Cancelling}@a21b990
                            println("JobCancellationException catched : $ex")
                        } finally {
                            println("The child is cancelled")
                        }
                    }
                    // Give our child a chance to execute and print using yield
                    yield()
                    println("Throwing an exception from the scope")
                    throw AssertionError() // TODO: app crashed
                }
            } catch (ex: Exception) {
                println("Caught an assertion error : $ex")
            }
        }
    }

    private fun test_supervision_scope2() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                supervisorScope {
                    launch {
                        throw Exception("子协程失败")
                    }
                    launch {
                        delay(100L)
                        println("尽管其他子协程失败了，这段代码仍然会被打印。")
                    }
                }
            } catch (ex: Exception) {
                println(ex)
            }
        }
    }


    private fun test(hello: TextView, fab: Button) {
//        flatMapMerge(hello, fab);
//        test_supervision_job();
        test_supervision_scope2();
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
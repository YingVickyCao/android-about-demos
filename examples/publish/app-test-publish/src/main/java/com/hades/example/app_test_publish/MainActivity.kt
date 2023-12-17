package com.hades.example.app_test_publish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.hades.example.android.libexamples.core.JsonUtils
import com.hades.example.android.libexamples.core.JsonUtils2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.test).setOnClickListener {
            // ERROR:java.lang.NoClassDefFoundError: Failed resolution of: Lcom/google/gson/GsonBuilder;
            test()
            test2()
        }
    }

    private fun test() {
        val tool1 = JsonUtils()
        val result1: String = tool1.toJson(3)
        println("toJson,$result1")
        val intValue: Int = tool1.fromJson<Int>("3", Int::class.java)
        println("fromJson,$intValue")

        Toast.makeText(this, "Kotlin-toJson=$result1,fromJson=$intValue", Toast.LENGTH_SHORT)
            .show()
    }

    private fun test2() {
        val tool1 = JsonUtils2()
        val result1: String = tool1.toJson(3)
        println("toJson,$result1")
        val intValue: Int = tool1.fromJson<Int>("3", Int::class.java)
        println("fromJson,$intValue")
        Toast.makeText(this, "Java-toJson=$result1,fromJson=$intValue", Toast.LENGTH_SHORT)
            .show()
    }
}
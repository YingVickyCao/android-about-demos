package com.hades.example.android.libexamples.data_binding

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.hades.example.android.libexamples.data_binding.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //    var user = User("", "");
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // way 1, START
        // binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // way 1, END
        // way 2, START
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // way 2, END

        // Binding data
        binding.user = User("Test", "User")
//        binding.user = user
//        user.firstName = "Test"
//        user.lastName = "User"

        findViewById<View>(R.id.setData).setOnClickListener { setData() }
        findViewById<View>(R.id.clearData).setOnClickListener { clearData() }
    }

    private fun setData() {
        binding.user = User("Test first name", "User")
//        user.firstName = "Test first name"
    }

    private fun clearData() {
        binding.user = User("", "")
//        user.firstName = ""
    }
}
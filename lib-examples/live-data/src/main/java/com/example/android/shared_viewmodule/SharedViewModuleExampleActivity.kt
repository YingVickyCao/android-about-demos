package com.example.android.shared_viewmodule

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.android.R

private const val TAG = "SharedLiveDataExampleActivity"

class StickyEventExampleActivity : AppCompatActivity() {
    val sharedViewModel: SharedViewModule by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared)

        bindViewModule()
    }

    private fun bindViewModule() {
//        sharedViewModel = ViewModelProvider(this).get(SharedViewModule::class.java)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        sharedViewModel.restore()
    }
}
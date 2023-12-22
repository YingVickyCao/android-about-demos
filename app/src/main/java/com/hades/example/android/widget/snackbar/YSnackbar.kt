package com.hades.example.android.widget.snackbar

import android.view.View
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class YSnackbar {
    companion object {
        fun Snackbar.make(view: View, text: CharSequence, @BaseTransientBottomBar.Duration duration: Int): Snackbar {
            val snackbar = Snackbar.make(view, text, duration)
            return snackbar
        }
    }
}
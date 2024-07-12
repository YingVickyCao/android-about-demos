package com.hades.example.android.base

import android.view.View

data class ViewsVisibilityHelper(
    val topic: View,
    val btnsContainer: View,
    val fragmentRoot: View
) {

    fun showBtns() {
        topic.visibility = View.VISIBLE
        btnsContainer.visibility = View.VISIBLE
        fragmentRoot.visibility = View.GONE
    }

    fun hideBtns() {
        topic.visibility = View.GONE
        btnsContainer.visibility = View.GONE
        fragmentRoot.visibility = View.VISIBLE
    }

    fun isBtnsHiden(): Boolean {
        return fragmentRoot.visibility == View.VISIBLE
    }
}
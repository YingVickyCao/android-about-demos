package com.hades.example.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class RedTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        setBackgroundColor(resources.getColor(android.R.color.holo_red_dark, getContext().theme))
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        setBackgroundColor(resources.getColor(android.R.color.holo_red_dark, getContext().theme))
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        setBackgroundColor(resources.getColor(android.R.color.holo_red_dark, getContext().theme))
    }
}
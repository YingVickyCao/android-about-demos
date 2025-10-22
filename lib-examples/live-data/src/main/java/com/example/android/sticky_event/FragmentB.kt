package com.example.android.sticky_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.android.R

class FragmentB : Fragment() {
    lateinit var sharedViewModel: SharedViewModule
    private lateinit var textView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_b, container, false)
        textView = view.findViewById<TextView>(R.id.timestamp)

        if (activity is StickyEventExampleActivity) {
            sharedViewModel = (activity as StickyEventExampleActivity).sharedViewModel
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.shared.observe(viewLifecycleOwner, object : Observer<Long> {
            override fun onChanged(value: Long) {
                activity?.runOnUiThread {
                    textView.text = value.toString()
                }
            }
        })

        sharedViewModel.one_time_event_shared.observe(viewLifecycleOwner, object : Observer<SingleEvent<Long>> {
            override fun onChanged(value: SingleEvent<Long>) {
                value.consume { it ->
                    activity?.runOnUiThread {
                        textView.text = it.toString()
                    }
                }
            }
        })
    }
}
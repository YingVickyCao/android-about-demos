package com.example.android.shared_viewmodule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.android.R
import kotlin.getValue

class FragmentA : Fragment() {
    val sharedViewModel: SharedViewModule by activityViewModels()
    private lateinit var textView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_a, container, false)
        textView = view.findViewById<TextView>(R.id.timestamp)

        view.findViewById<Button>(R.id.updateTimestamp).setOnClickListener {
            sharedViewModel.updateTimestamp()
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

        sharedViewModel.one_time_event_shared2.observe(viewLifecycleOwner, object : Observer<Long> {
            override fun onChanged(value: Long) {
                activity?.runOnUiThread {
                    textView.text = value.toString()
                }
            }
        })
    }
}
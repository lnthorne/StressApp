package com.example.stressapp

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class StressPlotFragment : Fragment() {

    companion object {
        fun newInstance() = StressPlotFragment()
    }

    private lateinit var viewModel: StressPlotViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_stress_plot, container, false)
        val btn: Button = view.findViewById(R.id.btnTest)

        btn.setOnClickListener() {
            Log.i("TestTag", StressDataController.getAllEntries().toString())
        }

        return view
    }

}
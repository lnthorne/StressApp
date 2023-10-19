package com.example.stressapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyViewPageAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PhotoSelectionFragment()
            1 -> StressPlotFragment()
            else -> throw IllegalArgumentException("Unexpected position $position")
        }
    }
}
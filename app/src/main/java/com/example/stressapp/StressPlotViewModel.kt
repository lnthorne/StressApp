package com.example.stressapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StressPlotViewModel : ViewModel() {
    private val _stressData = MutableLiveData<List<StressData>>()
    val stressData: LiveData<List<StressData>> get () = _stressData

    fun fetchStressData() {
        _stressData.value = StressDataController.getAllEntries()
    }
}
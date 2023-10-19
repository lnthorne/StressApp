package com.example.stressapp

import android.content.Context

object StressDataController {
    private val entries: MutableList<StressData> = mutableListOf()

    fun addEntry(context: Context, entry: StressData) {
        entries.add(entry)
        CSVFileHandler.writeEntry(context, entry)
    }

    fun loadEntriesFromStorage(context: Context) {
        val loadedEntries = CSVFileHandler.readEntries(context)
        entries.clear()
        entries.addAll(loadedEntries)
    }

    fun getAllEntries(): List<StressData> {
        return entries
    }
}
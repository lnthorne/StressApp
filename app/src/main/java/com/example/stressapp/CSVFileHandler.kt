package com.example.stressapp

import android.content.Context
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object CSVFileHandler {
    private const val FILENAME = "stress_timestamp.csv"

    fun writeEntry(context: Context, entry: StressData) {
        val file = File(context.filesDir, FILENAME)
        val writer = BufferedWriter(FileWriter(file, true))
        writer.write(entry.toCsvString())
        writer.close()
    }

    fun readEntries(context: Context): List<StressData> {
        val file = File(context.filesDir, FILENAME)
        if (!file.exists()) return emptyList()

        val entries = mutableListOf<StressData>()
        val reader = BufferedReader(FileReader(file))
        reader.useLines { lines ->
            lines.forEach {
                entries.add(StressData.fromCsvString(it))
            }
        }
        return entries
    }
}
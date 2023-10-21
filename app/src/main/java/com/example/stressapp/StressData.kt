package com.example.stressapp

import android.content.Context
import java.sql.Timestamp

data class StressData(
    val timestamp: Long,
    val stressLevel: Int
) {
    fun toCsvString(): String {
        return "$timestamp,$stressLevel\n"
    }

    companion object {
        fun fromCsvString(csvString: String): StressData {
            val parts = csvString.split(",")
            return StressData(parts[0].toLong(), parts[1].toInt())
        }
    }
}
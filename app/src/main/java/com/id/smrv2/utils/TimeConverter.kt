package com.id.smrv2.utils

object TimeConverter {
    private val timeRanges = listOf(
        "07:00-07:50",
        "07:50-08:40",
        "08:45-09:35",
        "09:35-10:25",
        "10:30-11:20",
        "11:20-12:10",
        "12:30-13:20",
        "13:20-14:10",
        "14:15-15:05",
        "15:15-16:05",
        "16:10-17:00",
        "17:00-17:50",
        "18:30-19:20",
        "19:20-20:10",
        "20:10-21:00",
        "16:00-16:50",
        "16:50-17:40",
        "17:40-18:30"
    )

    fun getTimeRange(hours: String): String {
        try {
            // Split the hours string by comma and convert to integers
            val hoursList = hours.split(",")
                .map { it.trim().toInt() }
            
            // Get the min and max hour indices
            val startIndex = hoursList.minOrNull()?.minus(1) ?: return "Invalid hours"
            val endIndex = hoursList.maxOrNull()?.minus(1) ?: return "Invalid hours"
            
            // Validate indices
            if (startIndex < 0 || endIndex >= timeRanges.size) {
                return "Time not available"
            }
            
            // Split the time ranges and get start and end times
            val startTime = timeRanges[startIndex].split("-")[0]
            val endTime = timeRanges[endIndex].split("-")[1]
            
            return "$startTime - $endTime"
        } catch (e: Exception) {
            return "Invalid format"
        }
    }

    // Keep the single hour conversion for backward compatibility
    fun getTimeRange(hour: Int): String {
        return if (hour in 1..timeRanges.size) {
            timeRanges[hour - 1]
        } else {
            "Invalid hour"
        }
    }
} 
package com.practice.board.domain.model

import java.util.concurrent.TimeUnit

class TrackingTime(private val millis: Long) {

    fun getMillis(): Long {
        return millis
    }

    fun getSeconds(): Long {
        return TimeUnit.MILLISECONDS.toSeconds(millis)
    }

    fun getTime(): String {
        val totalSecs = getSeconds()
        val hours = totalSecs / 3600
        val minutes = (totalSecs % 3600) / 60
        val seconds = totalSecs % 60

        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
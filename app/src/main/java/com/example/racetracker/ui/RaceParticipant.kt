package com.example.racetracker.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlin.coroutines.cancellation.CancellationException

class RaceParticipant(
    val name: String,
    val maxProgress: Int = 100,
    val progressDelayMillis: Long = 500L,
    private val progressIncrement: Int = 1,
    initialProgress: Int = 0,
) {
    init {
        require(maxProgress > 0) { "maxProgress=$maxProgress; must be > 0" }
        require(progressIncrement > 0) { "progressIncrement=$progressIncrement; must be > 0" }
    }

    var currentProgress by mutableIntStateOf(initialProgress)
        private set

    suspend fun run() {
        try {
            while (currentProgress < maxProgress) {
                delay(progressDelayMillis)
                currentProgress += progressIncrement
            }
        } catch (error: CancellationException) {
            Log.e("RaceParticipant", "$name: ${error.message}")
            throw error
        }
    }

    fun reset() {
        currentProgress = 0
    }
}

val RaceParticipant.progressFactor: Float
    get() = currentProgress / maxProgress.toFloat()
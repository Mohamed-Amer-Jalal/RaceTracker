package com.example.racetracker

import com.example.racetracker.ui.RaceParticipant
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test

class RaceParticipantTest {
    private val raceParticipant = RaceParticipant(
        name = "Player 1",
        maxProgress = 100,
        progressDelayMillis = 500L,
        progressIncrement = 1
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun raceParticipant_RaceStarted_ProgressUpdated() = runTest {
        launch { raceParticipant.run() }
        advanceTimeBy(raceParticipant.progressDelayMillis)
        runCurrent()
        assertEquals(1, raceParticipant.currentProgress)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun raceParticipant_RaceFinished_ProgressUpdated() = runTest {
        launch { raceParticipant.run() }
        advanceTimeBy(raceParticipant.maxProgress * raceParticipant.progressDelayMillis)
        runCurrent()
        assertEquals(100, raceParticipant.currentProgress)
    }
}